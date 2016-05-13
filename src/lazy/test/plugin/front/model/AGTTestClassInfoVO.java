package lazy.test.plugin.front.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyzhouqiang on 2015/12/7.
 */
public class AGTTestClassInfoVO {

    /**测试用例信息*/
    private String genPackageName;

    private List<String> genImportList;

    private String genPreName;

    private String genClassName;

    private String genPostName;

    private String genMethodName;

    private String genMethodParam;

    private String genConfigPath;

    /**被测试类信息*/
    private String testClassName;

    private String testClassVar;

    private String testMethodName;

    private String testMethodParam;

    private List<String> testParamList;

    private String testInvokeParam;

    private String testReturnObject;

    public String getGenPackageName() {
        return genPackageName;
    }

    public void setGenPackageName(String genPackageName) {
        this.genPackageName = genPackageName;
    }

    public void setGenImportList(List<String> genImportList) {
        this.genImportList = genImportList;
    }

    public String getGenPreName() {
        return genPreName;
    }

    public void setGenPreName(String genPreName) {
        this.genPreName = genPreName;
    }

    public String getGenClassName() {
        return genClassName;
    }

    public void setGenClassName(String genClassName) {
        this.genClassName = genClassName;
    }

    public String getGenPostName() {
        return genPostName;
    }

    public void setGenPostName(String genPostName) {
        this.genPostName = genPostName;
    }

    public String getGenMethodName() {
        return genMethodName;
    }

    public void setGenMethodName(String genMethodName) {
        this.genMethodName = genMethodName;
    }

    public String getGenMethodParam() {
        return genMethodParam;
    }

    public void setGenMethodParam(String genMethodParam) {
        this.genMethodParam = genMethodParam;
    }

    public String getGenConfigPath() {
        return genConfigPath;
    }

    public void setGenConfigPath(String genConfigPath) {
        this.genConfigPath = genConfigPath;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestClassVar() {
        return testClassVar;
    }

    public void setTestClassVar(String testClassVar) {
        this.testClassVar = testClassVar;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getTestMethodParam() {
        return testMethodParam;
    }

    public void setTestMethodParam(String testMethodParam) {
        this.testMethodParam = testMethodParam;
    }

    public String getTestInvokeParam() {
        return testInvokeParam;
    }

    public void setTestInvokeParam(String testInvokeParam) {
        this.testInvokeParam = testInvokeParam;
    }

    public String getTestReturnObject() {
        return testReturnObject;
    }

    public void setTestReturnObject(String testReturnObject) {
        this.testReturnObject = testReturnObject;
    }

    public void setTestParamList(List<String> testParamList) {
        this.testParamList = testParamList;
    }

    public List<String> getGenImportList() {
        if (genImportList == null) {
            synchronized (this) {
                if (genImportList == null) {
                    genImportList = new ArrayList<String>();
                }
            }
        }
        return genImportList;
    }

    public List<String> getTestParamList() {
        if (testParamList == null) {
            synchronized (this) {
                if (testParamList == null) {
                    testParamList = new ArrayList<String>();
                }
            }
        }
        return testParamList;
    }

    @Override
    public String toString() {
        return "AGTTestClassInfoVO{" +
                "genPackageName='" + genPackageName + '\'' +
                ", genImportList=" + genImportList +
                ", genPreName='" + genPreName + '\'' +
                ", genClassName='" + genClassName + '\'' +
                ", genPostName='" + genPostName + '\'' +
                ", genMethodName='" + genMethodName + '\'' +
                ", genMethodParam='" + genMethodParam + '\'' +
                ", genConfigPath='" + genConfigPath + '\'' +
                ", testClassName='" + testClassName + '\'' +
                ", testClassVar='" + testClassVar + '\'' +
                ", testMethodName='" + testMethodName + '\'' +
                ", testMethodParam='" + testMethodParam + '\'' +
                ", testParamList=" + testParamList +
                ", testInvokeParam='" + testInvokeParam + '\'' +
                ", testReturnObject='" + testReturnObject + '\'' +
                '}';
    }
}
