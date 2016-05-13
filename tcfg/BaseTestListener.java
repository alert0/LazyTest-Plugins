package common;

import org.apache.commons.lang3.StringUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BaseTestListener implements IInvokedMethodListener{

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod()) {
            return;
        }

        Object testCase = testResult.getInstance();
        Object[] argArray = testResult.getParameters();
        if (argArray == null || argArray.length == 0) {
            return;
        }

        //TESTNG数据驱动测试
        if (!StringUtils.equals("1", StringUtils.strip(String.valueOf(argArray[0])))) {
            throw new SkipException("Test Case Skipped: " + argArray[1]);
        }
        try {
            CountDownLatch latch = getTestLatch(testCase, "getTestLatch");
            latch.countDown();
            latch.await();
            Method preMethod = this.findConfigureBeforeMethod(testCase, String.valueOf(argArray[1]));
            if (preMethod != null) {
                preMethod.invoke(testCase, Arrays.copyOfRange(argArray, 3, argArray.length));
            }
        } catch (Exception e) {
            testResult.setThrowable(e);
            testResult.setStatus(ITestResult.FAILURE);
        }
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (!method.isTestMethod() || !testResult.isSuccess()) {
            return;
        }

        Object testCase = testResult.getInstance();
        Object[] paramArray = testResult.getParameters();
        if (paramArray == null || paramArray.length == 0) {
            return;
        }

        //TESTNG数据驱动测试
        try {
            Object resultObj = getTestResult(testCase, "getTestResult");
            Object[] argArray = genMethodArgArray(paramArray, resultObj);
            Method afterMethod = this.findConfigureAfterMethod(testCase, String.valueOf(argArray[1]));
            if (afterMethod != null) {
                afterMethod.invoke(testCase, Arrays.copyOfRange(argArray, 3, argArray.length));
            }
        } catch (Exception e) {
            testResult.setThrowable(e);
            testResult.setStatus(ITestResult.FAILURE);
        }
    }

    private Object[] genMethodArgArray(Object[] paramArray, Object resultObj) {
        List list = new ArrayList();
        if (paramArray != null && paramArray.length != 0) {
            list.addAll(Arrays.asList(paramArray));
        }
        //if (resultObj != null) {
            list.add(resultObj);
        //}
        return list.toArray();
    }

    private Method findConfigureBeforeMethod(Object testCase, String testCode) {
        Class clazz = testCase.getClass();
        Method[] methodArray = clazz.getDeclaredMethods();
        for (Method method : methodArray) {
            if (method.isAnnotationPresent(BaseTestBefore.class)) {
                BaseTestBefore beforeAnnotation = method.getAnnotation(BaseTestBefore.class);
                if (StringUtils.equals(beforeAnnotation.testCode(), testCode)) {
                    return method;
                }
            }
        }
        return null;
    }

    private Method findConfigureAfterMethod(Object testCase, String testCode) {
        Class clazz = testCase.getClass();
        Method[] methodArray = clazz.getDeclaredMethods();
        for (Method method : methodArray) {
            if (method.isAnnotationPresent(BaseTestAfter.class)) {
                BaseTestAfter afterAnnotation = method.getAnnotation(BaseTestAfter.class);
                if (StringUtils.equals(afterAnnotation.testCode(), testCode)) {
                    return method;
                }
            }
        }
        return null;
    }

    private Object getTestResult(Object testCase, String methodName) throws Exception {
    	Class<?> baseTest = testCase.getClass().getSuperclass();
        
        while(!baseTest.getSimpleName().equals("BaseTestCase")) {
            baseTest = baseTest.getSuperclass();
        }
    	
    	Method method = baseTest.getDeclaredMethod(methodName);
        return method.invoke(testCase);
    }

    private CountDownLatch getTestLatch(Object testCase, String methodName) throws Exception {
    	Class<?> baseTest = testCase.getClass().getSuperclass();
        
        while(!baseTest.getSimpleName().equals("BaseTestCase")) {
            baseTest = baseTest.getSuperclass();
        }
    	
    	Method method = baseTest.getDeclaredMethod(methodName);
        return (CountDownLatch) method.invoke(testCase);
    }
}
