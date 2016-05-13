package lazy.test.plugin.fundation.processor;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.IStructuredSelection;

import java.util.ArrayList;
import java.util.List;

import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.ClassTransferHandler;
import lazy.test.plugin.fundation.model.ClassMetaDataModel;
import lazy.test.plugin.fundation.model.MethodMetaDataModel;
import lazy.test.plugin.fundation.model.ParamMetaDataModel;
import lazy.test.plugin.fundation.model.ReturnMetaDataModel;

/**
 * Created by wyzhouqiang on 2015/11/26.
 */
public class ClassDataParseProcessor {

    public static ClassMetaDataModel parseSelectedMethod(IStructuredSelection selection) {
        ClassMetaDataModel model = new ClassMetaDataModel();
        IMember member = ((IMember)selection.getFirstElement());

        if (StringUtils.equals(member.getElementName(), member.getClassFile().getType().getElementName())) {
            throw new AGTBizException("构造方法无法生成测试用例", "EXP00001");
        }

        generatePackage(model, member);
        generateClass(model, member);
        generateMethod(model, member);

        return model;
    }

    private static void generatePackage(ClassMetaDataModel model, IMember member) {
        IPackageFragment packageFragment = member.getClassFile().getType().getPackageFragment();
        model.setPackageQualifiedName(packageFragment.getElementName());
    }

    private static void generateClass(ClassMetaDataModel model, IMember member) {
        model.setClassSimpleName(member.getClassFile().getType().getElementName());
        model.setClassQualifiedName(member.getClassFile().getType().getFullyQualifiedName());
    }

    private static void generateMethod(ClassMetaDataModel model, IMember member) {
        MethodMetaDataModel method = new MethodMetaDataModel();

        IJavaElement element = member.getPrimaryElement();
        IJavaElement parent = element.getParent();
        String pathStr = null;
        if (StringUtils.strip(parent.toString()).startsWith("interface")) {
            pathStr = StringUtils.strip(StringUtils.replaceOnce(parent.toString(), "interface", "[in")) + "]";
        } else if (StringUtils.strip(parent.toString()).startsWith("class")) {
            pathStr = StringUtils.strip(StringUtils.replaceOnce(parent.toString(), "class", "[in")) + "]";
        }
        String nameStr = StringUtils.strip(element.getElementName());
        String rawStr = StringUtils.strip(StringUtils.replace(element.toString(), pathStr, ""));
        String paramStr = StringUtils.strip(StringUtils.substringBetween(rawStr, "(", ")"));
        String returnStr = StringUtils.strip(StringUtils.replace(StringUtils.replace(rawStr, "(" + paramStr + ")", ""), nameStr, ""));

        method.setMethodSimpleName(nameStr);
        method.setParamList(handleMethodParamList(paramStr, element));
        method.setReturnObject(handleClassMethodReturn(returnStr));
        method.setMethodQualifiedName(handleParamQualifiedName(nameStr, method.getParamList()));

        model.setMethodMetaDataModel(method);
    }

    private static String handleParamQualifiedName(String nameStr, List<ParamMetaDataModel> paramList) {
        StringBuffer sb = new StringBuffer(nameStr);
        sb.append("(");

        for (ParamMetaDataModel param : paramList) {
            sb.append(ClassTransferHandler.shortGenericClassName(param.getParamStr()));
            sb.append(" ").append(param.getParamName()).append(", ");
        }
        if (sb.lastIndexOf(",") != -1) {
            sb.deleteCharAt(sb.lastIndexOf(","));
        }
        if (sb.lastIndexOf(" ") != -1) {
            sb.deleteCharAt(sb.lastIndexOf(" "));
        }
        sb.append(")");

        return sb.toString();
    }

    private static List<ParamMetaDataModel> handleMethodParamList(String paramStr, IJavaElement element) {
        List<ParamMetaDataModel> list = new ArrayList<ParamMetaDataModel>();
        String [] paramNameArray = null;
        try {
        	if (element instanceof IMethod) {
                paramNameArray = ((IMethod) element).getParameterNames();
        	}
        } catch (Exception e) {
            throw new AGTBizException("无法处理的方法参数类型", "EXP00002", e);
        }

        int i = 0;
        for (String type : ClassTransferHandler.splitRawParamString(paramStr)) {
            ParamMetaDataModel param = new ParamMetaDataModel();
            if (paramNameArray != null && paramNameArray[i] != null) {
                param.setParamName(paramNameArray[i]);
            } else {
                param.setParamName("arg" + i);
            }
            
            param.setParamStr(type);
            param.setParamMap(ClassTransferHandler.convertClassTypeMap(type));
            list.add(param);
            i++;
        }
        return list;
    }

    private static ReturnMetaDataModel handleClassMethodReturn(String returnStr) {
        if (StringUtils.contains(returnStr, "static")) {
            returnStr = StringUtils.replace(returnStr, "static", "");
        }
        ReturnMetaDataModel returnObject = new ReturnMetaDataModel();
        returnObject.setReturnStr(ClassTransferHandler.shortGenericClassName(returnStr));
        returnObject.setReturnMap(ClassTransferHandler.convertClassTypeMap(returnStr));
        return returnObject;
    }

}
