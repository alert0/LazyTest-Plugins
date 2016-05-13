package lazy.test.plugin.fundation.handler;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;

/**
 * Created by wyzhouqiang on 2015/11/28.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ClassTransferHandler {

    public static Map<String, Object> convertClassTypeMap(String type) {
        type = StringUtils.strip(type);

        Map<String, Object> map = new HashMap<String, Object>();

        //Map<String, String>
        if (isPrimitiveType(type)) {
            map.put(AGTCommonProperties.AST_PARSE_TYPE_PRIMITIVE, type);
            return map;
        }
        //Map<String, String>
        if (isSimpleType(type)) {
            map.put(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE, type);
            return map;
        }
        //Map<String, List<Map>>递归, 最后一级以Map<String, String>结束
        if (isArrayType(type)) {
            String subStr = StringUtils.substringBeforeLast(type, "[");
            List<Map> loopList = new ArrayList<Map>();
            loopList.add(convertClassTypeMap(subStr));
            map.put(AGTCommonProperties.AST_PARSE_TYPE_ARRAY, loopList);
            return map;
        }
        //Map<String, List<Map>>递归, 最后一级以Map<String, String>结束
        if (isParameterType(type)) {
            String rawName = StringUtils.substringBefore(type, "<");
            List<Map> loopList = new ArrayList<Map>();

            Map<String, String> rawNameMap = new HashMap<String, String>();
            rawNameMap.put(AGTCommonProperties.AST_PARSE_KEY_PARAM_CLASS, StringUtils.strip(rawName));
            loopList.add(rawNameMap);

            String paramStr = StringUtils.strip(StringUtils.substring(type, rawName.length() + 1, type.length() -1));
            for (String temp : splitRawParamString(paramStr)) {
                loopList.add(convertClassTypeMap(temp));
            }
            map.put(AGTCommonProperties.AST_PARSE_TYPE_PARAM, loopList);
            return map;
        }

        throw new AGTBizException("不支持的参数类型", "EXP00002");
    }

    public static List<String> splitRawParamString(String paramStr) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isBlank(paramStr)) {
            return list;
        }
        if (!StringUtils.contains(paramStr, ",")) {
            list.add(StringUtils.strip(paramStr));
            return list;
        }

        String[] strArray = StringUtils.split(paramStr, ",");
        int cnt = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strArray.length; i++) {
            if (StringUtils.contains(strArray[i], "<") && !StringUtils.contains(strArray[i], ">")) {
                sb.append(strArray[i]).append(",");
                cnt++;
            } else if (StringUtils.contains(strArray[i], ">") && !StringUtils.contains(strArray[i], "<")) {
                sb.append(strArray[i]);
                cnt--;
            } else {
                sb.append(strArray[i]);
            }
            if (cnt == 0) {
                list.add(StringUtils.strip(sb.toString()));
                sb = new StringBuffer();
                continue;
            }
            if (checkMatch(sb.toString())) {
                list.add(StringUtils.strip(sb.toString()));
                sb = new StringBuffer();
                cnt = 0;
                continue;
            }
            if (i == strArray.length - 1) {
                for (int j = 0; j < cnt - 1; j++) {
                    sb.append(">");
                }
                list.add(StringUtils.strip(sb.toString()));
            }
        }

        return list;
    }

    public static String shortGenericClassName(String rawName) {
        if (!StringUtils.contains(rawName, ".")) {
            return rawName;
        }
        if (!StringUtils.contains(rawName, "<")) {
            String [] splitArray = StringUtils.split(rawName, ".");
            return splitArray[splitArray.length - 1];
        }
        StringBuilder sb = new StringBuilder();
        String typeStr = StringUtils.substringBefore(rawName, "<");
        String paramStr = StringUtils.strip(StringUtils.substring(rawName, typeStr.length() + 1, rawName.length()));
        sb.append(shortGenericClassName(typeStr));
        sb.append("<");
        for (String temp : splitRawParamString(paramStr)) {
            sb.append(shortGenericClassName(temp));
            sb.append(", ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    public static List<String> fetchAllClassType(Map<String, Object> map) {
        List<String> list = new ArrayList<String>();

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (StringUtils.equals(AGTCommonProperties.AST_PARSE_TYPE_PRIMITIVE, entry.getKey())) {
                list.add((String)entry.getValue());
                continue;
            }
            if (StringUtils.equals(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE, entry.getKey())) {
                list.add((String)entry.getValue());
                continue;
            }
            if (StringUtils.equals(AGTCommonProperties.AST_PARSE_KEY_PARAM_CLASS, entry.getKey())) {
                list.add((String)entry.getValue());
                continue;
            }
            for (Map<String, Object> tempMap : (List<Map<String, Object>>)entry.getValue()) {
                list.addAll(fetchAllClassType(tempMap));
            }
        }

        return list;
    }

    public static Object createObject(Map<String, Object> map, ClassLoader loader) {
        if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE)) {
            String className = (String)map.get(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE);
            Object obj = createInstance(className, loader);
            fillDefaultAttributes(obj);
            return obj;
        }
        if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_ARRAY)) {
            String arrayType = searchArrayType((List<Map>) map.get(AGTCommonProperties.AST_PARSE_TYPE_ARRAY));
            Class clazz = createClassType(arrayType, loader);
            Object result = Array.newInstance(clazz, 1);
            Map<String, Object> temp = ((List<Map<String, Object>>)map.get(AGTCommonProperties.AST_PARSE_TYPE_ARRAY)).get(0);

            Object obj = createObject(temp, loader);
            Array.set(result, 0, obj);
            return result;
        }
        if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_PARAM)) {
            Object param = null;
            List paramArg = new ArrayList();
            for (Map<String, Object> temp : (List<Map<String, Object>>)map.get(AGTCommonProperties.AST_PARSE_TYPE_PARAM)) {
                if (temp.containsKey(AGTCommonProperties.AST_PARSE_KEY_PARAM_CLASS)) {
                    String paramType = (String)temp.get(AGTCommonProperties.AST_PARSE_KEY_PARAM_CLASS);
                    param = createInstance(paramType, loader);
                    fillDefaultAttributes(param);
                    continue;
                }
                paramArg.add(createObject(temp, loader));
            }
            return buildParameterObject(param, paramArg);
        }
        return map;
    }

    public static void fillAllStringField(Object obj, String fieldName) {
        Class clazz = obj.getClass();
        if (clazz.isArray()) {
            if (isStringType(clazz.getComponentType())) {
                Array.set(obj, 0, fieldName);
            } else {
                fillAllStringField(Array.get(obj, 0), fieldName);
            }
            return;
        }
        if (obj instanceof Map) {
            List removeList = new ArrayList();
            Set<Map.Entry> entrySet = ((Map) obj).entrySet();
            for (Map.Entry entry : entrySet) {
                if (isStringType(entry.getKey().getClass())) {
                    removeList.add(entry.getKey());
                } else {
                    fillAllStringField(entry.getKey(), fieldName);
                }
            }
            for (Map.Entry entry : entrySet) {
                if (isStringType(entry.getValue().getClass())) {
                    ((Map) obj).put(entry.getKey(), fieldName);
                } else {
                    fillAllStringField(entry.getValue(), fieldName);
                }
            }
            for (Object tempKey : removeList) {
                ((Map) obj).put(fieldName, ((Map) obj).get(tempKey));
                ((Map) obj).remove(tempKey);
            }
            return;
        }
        if (obj instanceof Set) {
            List removeList = new ArrayList();
            for (Object temp : (Set) obj) {
                if (isStringType(temp.getClass())) {
                    removeList.add(temp);
                } else {
                    fillAllStringField(temp, fieldName);
                }
            }
            if (!removeList.isEmpty()) {
                ((Set) obj).add(fieldName);
                ((Set) obj).removeAll(removeList);
            }
            return;
        }
        if (obj instanceof List) {
            List removeList = new ArrayList();
            for (Object temp : (List) obj) {
                if (isStringType(temp.getClass())) {
                    removeList.add(temp);
                } else {
                    fillAllStringField(((List) obj).get(0), fieldName);
                }
            }
            if (!removeList.isEmpty()) {
                ((List) obj).add(fieldName);
                ((List) obj).removeAll(removeList);
            }
            return;
        }
        if (clazz.isEnum() || clazz.isPrimitive() || isDecorationType(clazz)) {
            return;
        }
        if (clazz.getName().startsWith("java") || clazz.getName().startsWith("javax") || clazz.getName().startsWith("com.sun")) {
            return;
        }
        List<Field> fieldList = fetchAllFieldList(clazz);
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                if (String.class.isAssignableFrom(field.getType())) {
                    field.set(obj, field.getName());
                } else {
                    fillAllStringField(field.get(obj), field.getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            field.setAccessible(false);
        }
    }

    private static boolean isStringType(Class clazz) {
        return String.class.isAssignableFrom(clazz);
    }

    private static String searchArrayType(List<Map> list) {
        Map<String, Object> map = list.get(0);
        if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_ARRAY)) {
            searchArrayType((List<Map>) map.get(AGTCommonProperties.AST_PARSE_TYPE_ARRAY));
        }
        if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_PARAM)) {
            for (Map<String, Object> temp : (List<Map<String, Object>>)map.get(AGTCommonProperties.AST_PARSE_TYPE_PARAM)) {
                if (temp.containsKey(AGTCommonProperties.AST_PARSE_KEY_PARAM_CLASS)) {
                    return (String)temp.get(AGTCommonProperties.AST_PARSE_KEY_PARAM_CLASS);
                }
            }
        }
        if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE)) {
            return (String)map.get(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE);
        }
        throw new AGTBizException("创建参数对象失败", "EXP00007");
    }

    protected static void fillDefaultAttributes(Object instance) {
        if (instance.getClass().getName().startsWith("java") || instance.getClass().getName().startsWith("javax") ||instance.getClass().getName().startsWith("com.sun")) {
            return;
        }
        if (instance.getClass().isEnum()) {
            return;
        }

        List<Field> fieldList = fetchAllFieldList(instance.getClass());

        if (fieldList.isEmpty()) {
            return;
        }

        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object fieldObj = genGenericTypeObject(field.getGenericType());
                field.set(instance, fieldObj);
            } catch (Exception e) {
                MessageNoticeHandler.logError("创建对象失败: " + instance.getClass().getName(), e);
            }
            field.setAccessible(false);
        }
    }

    protected static List<Field> fetchAllFieldList(Class clazz) {
        List<Field> fieldList = new ArrayList<Field>();
        Field[] thisFieldArray = clazz.getDeclaredFields();
        if (thisFieldArray != null && thisFieldArray.length != 0) {
        	for (Field field : thisFieldArray) {
                if (Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                fieldList.add(field);
            }
        }

        Class superClass = clazz.getSuperclass();
        if (superClass != null && !StringUtils.equals(superClass.getName(), "java.lang.Object")) {
            fieldList.addAll(fetchAllFieldList(superClass));
        }
        return fieldList;
    }

    protected static Object createInstance(String className, ClassLoader loader) {
        try {
            String type = MessageMappingHandler.findMappingResult(className);
            Class clazz = createClassType(type, loader);

            if (clazz.isPrimitive() || isDecorationType(clazz)) {
                if (char.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                    return '0';
                } else {
                    Constructor primitiveConstructor = clazz.getConstructor(String.class);
                    return primitiveConstructor.newInstance("0");
                }
            }
            if (clazz.isEnum()) {
                return clazz.getEnumConstants()[0];
            }
            if (clazz.isAssignableFrom(Object.class)) {
                return "object";
            }
            if (clazz.isAssignableFrom(BigDecimal.class) || clazz.isAssignableFrom(BigInteger.class)) {
            	return clazz.getConstructor(String.class).newInstance("0");
            }

            Constructor constructor = findMinVarsConstructor(clazz);
            Type[] paramTypeArray = constructor.getGenericParameterTypes();
            if (paramTypeArray.length == 0) {
                return constructor.newInstance();
            }
            List paramList = new ArrayList();
            for (Type paramType : paramTypeArray) {
                paramList.add(genGenericTypeObject(paramType));
            }
            return constructor.newInstance(paramList.toArray());
        } catch (Exception e) {
            MessageNoticeHandler.logError("创建对象失败: " + className, e);
        }
        return null;
    }

    protected static Object createInstance(Class clazz) {
        try {
            String type = MessageMappingHandler.findMappingResult(clazz.getName());
            if (!StringUtils.equals(type, clazz.getName())) {
                clazz = Class.forName(type);
            }

            if (clazz.isPrimitive() || isDecorationType(clazz)) {
                if (char.class.isAssignableFrom(clazz) || Character.class.isAssignableFrom(clazz)) {
                    return '0';
                } else {
                    Constructor primitiveConstructor = clazz.getConstructor(String.class);
                    return primitiveConstructor.newInstance("0");
                }
            }
            if (clazz.isEnum()) {
                return clazz.getEnumConstants()[0];
            }
            if (clazz.isAssignableFrom(Object.class)) {
                return "object";
            }
            if (clazz.isAssignableFrom(BigDecimal.class) || clazz.isAssignableFrom(BigInteger.class)) {
            	return clazz.getConstructor(String.class).newInstance("0");
            }

            Constructor constructor = findMinVarsConstructor(clazz);
            Type[] paramTypeArray = constructor.getGenericParameterTypes();
            if (paramTypeArray.length == 0) {
                return constructor.newInstance();
            }
            List paramList = new ArrayList();
            for (Type paramType : paramTypeArray) {
                paramList.add(genGenericTypeObject(paramType));
            }
            return constructor.newInstance(paramList.toArray());
        } catch (Exception e) {
            MessageNoticeHandler.logError("创建对象失败: " + clazz, e);
        }
        return null;
    }

    protected static Constructor findMinVarsConstructor(Class clazz) {
        Constructor[] array = clazz.getConstructors();
        int varNum = Integer.MAX_VALUE;
        int arrayLoc = -1;
        boolean findConstructor = false;
        for (int i = 0; i < array.length; i++) {
            Class[] paramTypeArray = array[i].getParameterTypes();
            if (paramTypeArray.length == 0) {
                return array[i];
            }
            if (paramTypeArray.length < varNum) {
                findConstructor = true;
                varNum = paramTypeArray.length;
                arrayLoc = i;
            }
        }
        if (findConstructor) {
            return array[arrayLoc];
        }
        return null;
    }

    protected static Class createClassType(String className, ClassLoader loader) {
        try {
            return loader.loadClass(className);
        } catch (Exception e) {
            throw new AGTBizException("创建参数对象失败", "EXP00007", e);
        }
    }

    protected static Object genGenericTypeObject(Type type) throws Exception {
        if (type instanceof ParameterizedType) {
            return genParamTypeObject((ParameterizedType) type);
        }
        if (type instanceof TypeVariable) {
            return new Object();
        }
        if (type instanceof Class) {
            Class fieldClass = (Class) type;
            if (fieldClass.isArray()) {
                Class componentClass = fieldClass.getComponentType();
                Object arrayObj = Array.newInstance(componentClass, 1);
                Object componentObj = createInstance(componentClass);
                fillDefaultAttributes(componentObj);
                Array.set(arrayObj, 0, componentObj);
                return arrayObj;
            }
            Object obj = createInstance(fieldClass);
            fillDefaultAttributes(obj);
            return obj;
        }
        return null;
    }

    private static Object genParamTypeObject(ParameterizedType type) throws Exception {
        Class rawClass = (Class)type.getRawType();
        Object rawObj = createInstance(rawClass);
        Type[] argTypeArray = type.getActualTypeArguments();
        List argList = new ArrayList();
        for (Type argType : argTypeArray) {
            if (argType instanceof Class) {
                Object arg = createInstance((Class) argType);
                fillDefaultAttributes(arg);
                argList.add(arg);
            }
            if (argType instanceof ParameterizedType) {
                Object arg = genParamTypeObject((ParameterizedType) argType);
                argList.add(arg);
            }
        }

        return buildParameterObject(rawObj, argList);
    }

    /**
     * 目前支持List, Set, Map, 自定义泛型参数(如Foo<K, V>)的注入
     */
    private static Object buildParameterObject(Object param, List paramArg) {
        if (param instanceof List) {
            ((List)param).addAll(paramArg);
            return param;
        }
        if (param instanceof Set) {
            ((Set)param).addAll(paramArg);
            return param;
        }
        if (param instanceof Map) {
            ((Map)param).put(paramArg.get(0), paramArg.get(1));
            return param;
        }

        Class clazz = param.getClass();

        TypeVariable<Class>[] typeVarArray = clazz.getTypeParameters();
        if (typeVarArray != null && typeVarArray.length > 0) {
            for(int i = 0; i < typeVarArray.length; i++) {
                Field field = searchFieldByTypeName(clazz, typeVarArray[i].getName());
                if (field == null) {
                    continue;
                }
                field.setAccessible(true);
                try {
                    field.set(param, paramArg.get(i));
                } catch (Exception e) {
                    MessageNoticeHandler.logError("创建对象失败: " + param, e);
                }
                field.setAccessible(false);
            }
        }
        return param;
    }

    private static Field searchFieldByTypeName(Class clazz, String name) {
        Field[] fieldArray = clazz.getDeclaredFields();
        for (Field field : fieldArray) {
            Type type = field.getGenericType();
            if (type instanceof TypeVariable && StringUtils.equals(name, ((TypeVariable) type).getName())) {
                return field;
            }
        }
        return null;
    }

    private static boolean checkMatch(String paramStr) {
        return StringUtils.countMatches(paramStr, "<") == StringUtils.countMatches(paramStr, ">");
    }

    private static boolean isPrimitiveType(String type) {
        return StringUtils.indexOf(type, ".") == -1 && StringUtils.equals(type, StringUtils.lowerCase(type));
    }

    private static boolean isArrayType(String type) {
        return StringUtils.contains(type, "[") && StringUtils.contains(type, "]")
                && StringUtils.length(type) - 1 == StringUtils.lastIndexOf(type, "]");
    }

    private static boolean isParameterType(String type) {
        return StringUtils.contains(type, "<") && StringUtils.contains(type, ">")
                && StringUtils.length(type) - 1 == StringUtils.lastIndexOf(type, ">");
    }

    private static boolean isSimpleType(String type) {
        return type.matches("^\\w+[\\.\\w]*\\.\\w+$");
    }

    private static boolean isDecorationType(Class clazz) {
        boolean flag = false;
        if (Boolean.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Character.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Byte.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Short.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Integer.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Long.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Float.class.isAssignableFrom(clazz)) {
            flag = true;
        } else if (Double.class.isAssignableFrom(clazz)) {
            flag = true;
        }
        return flag;
    }

}
