package common;

import au.com.bytecode.opencsv.CSVReader;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang3.StringUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

@ContextConfiguration(locations ={"classpath*:spring-test.xml"})
@Listeners({BaseTestListener.class})
public class BaseTestCase extends AbstractTestNGSpringContextTests {

    private static final ConcurrentHashMap<String, Object[][]> DATA_CACHE = new ConcurrentHashMap<String, Object[][]>();

    private Object _testResult;

    private CountDownLatch _testLatch;

    @BeforeClass(dependsOnMethods={"springTestContextPrepareTestInstance"})
    public void beforeAbstractClass() {
        initTestLatch();
    }

    @AfterClass
    public void afterAbstractClass() {
    }

    @BeforeMethod
    public void beforeAbstractMethod() {
    }

    @AfterMethod
    public void afterAbstractMethod() {
    }

    @DataProvider
    public Object[][] csvDataProvider() {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        CSVReader csvReader = null;
        try {
            String configPath = findConfigPath();
            if (DATA_CACHE.containsKey(configPath)) {
                return DATA_CACHE.get(configPath);
            }
            File file = findFileConfig(configPath);
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "GBK");
            csvReader = new CSVReader(isr);
            List<String[]> csvDataList = csvReader.readAll();
            if (csvDataList != null && !csvDataList.isEmpty()) {
                String[] headerArray = csvDataList.get(0);
                List<String[]> dataList = csvDataList.subList(1, csvDataList.size());

                List<Object[]> resultList = new ArrayList<Object[]>();
                for (String[] dataArray : dataList) {
                    List<Object> rowData = new ArrayList<Object>();
                    for (int i = 0; i < headerArray.length; i++) {
                        if (!StringUtils.contains(headerArray[i], "_{")) {
                            rowData.add(dataArray[i]);
                        } else if (headerArray[i].startsWith("java.lang")) {
                            Object obj = getSimpleObject(StringUtils.substringBefore(headerArray[i], "_{"), dataArray[i]);
                            rowData.add(obj);
                        } else {
                            Object obj = getComplexObject(configPath, dataArray[i]);
                            rowData.add(obj);
                        }
                    }
                    resultList.add(rowData.toArray());
                }
                Object[][] dataResult = resultList.toArray(new Object[csvDataList.size() - 1][headerArray.length]);
                DATA_CACHE.put(configPath, dataResult);
                return dataResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("csvDataProvider loading failed");
    }

    private Object getComplexObject(String configPath, String dataStr) {
        if (StringUtils.contains(dataStr, ".xml")) {
            XStream xStream = new XStream();
            File file = findObjectConfig(configPath, dataStr);
            return xStream.fromXML(file);
        }
        return null;
    }

    private Object getSimpleObject(String headStr, String dataStr) {
        if (StringUtils.equals("java.lang.Object", headStr)) {
            return dataStr;
        }
        if (StringUtils.equals("java.lang.Character", headStr)) {
            return dataStr.charAt(0);
        }
        try {
            Class clazz = Class.forName(headStr);
            if (clazz.isEnum()) {
                Object[] objArray = clazz.getEnumConstants();
                for (Object obj : objArray) {
                    if (StringUtils.equals(obj.toString(), dataStr)) {
                        return obj;
                    }
                }
                return null;
            }
            Constructor[] constructorArray = clazz.getConstructors();
            if (constructorArray != null && constructorArray.length != 0) {
                for (Constructor constructor : constructorArray) {
                    Class[] paramTypes = constructor.getParameterTypes();
                    if(paramTypes != null && paramTypes.length == 1 && String.class.isAssignableFrom(paramTypes[0])) {
                        return constructor.newInstance(dataStr);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected File findFileConfig(String configPath) {
        try {
            File baseDir = new File(this.getClass().getClassLoader().getResource(configPath).toURI());
            if (baseDir != null) {
                File[] files = baseDir.listFiles();
                if (files != null && files.length != 0) {
                    for (File temp : files) {
                        if (temp.isFile() && StringUtils.contains(temp.getName(), ".csv")) {
                            return temp;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("no configure file found");
    }

    private File findObjectConfig(String configPath, String dataStr) {
        try {
            File baseDir = new File(this.getClass().getClassLoader().getResource(configPath).toURI());
            File file = findNestedFile(baseDir, dataStr);
            if (file != null) {
                return file;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("no configure file found");
    }

    private File findNestedFile(File baseDir, String fileName) {
        File[] files = baseDir.listFiles();
        if (files == null || files.length == 0) {
            return null;
        }
        for (File temp : files) {
            if (temp.isDirectory()) {
                File result = findNestedFile(temp, fileName);
                if (result != null) {
                    return result;
                }
            }
            if (temp.isFile() && StringUtils.equals(fileName, temp.getName())) {
                return temp;
            }
        }
        return null;
    }

    private String findConfigPath() {
        Method[] methodArray = this.getClass().getDeclaredMethods();
        for (Method method : methodArray) {
            if (method.isAnnotationPresent(BaseTestConfig.class)) {
                BaseTestConfig configAnnotation = method.getAnnotation(BaseTestConfig.class);
                return configAnnotation.testPath();
            }
        }
        return null;
    }

    private void initTestLatch() {
        Method[] methodArray = this.getClass().getDeclaredMethods();
        for (Method method : methodArray) {
            if (method.isAnnotationPresent(Test.class)) {
                Test configAnnotation = method.getAnnotation(Test.class);
                _testLatch = new CountDownLatch(configAnnotation.threadPoolSize());
                return;
            }
        }
    }

    /**
     * TESTNG Listener使用, 请勿改变方法名
     */
    Object getTestResult() {
        return _testResult;
    }

    /**
     * TESTNG Listener使用, 请勿改变方法名
     */
    CountDownLatch getTestLatch() {
        return _testLatch;
    }

    protected void setTestResult(Object _testResult) {
        this._testResult = _testResult;
    }

    protected synchronized String getTimeSnapshot(){
        String  timeSnapshot;
        timeSnapshot = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return timeSnapshot;
    }
}