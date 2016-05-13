package lazy.test.plugin.fundation.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyzhouqiang on 2015/11/27.
 */
public class TestCaseDataModel {

    private String genTestCodePath;

    private String genTestPrePath;

    private String genTestPostPath;

    private String genTestDataPath;

    private String genProjectName;

    private String genProjectPath;

    private String genPackageName;

    private String genHttpUrl;

    private String genClassPre;

    private String genClassName;

    private String genClassPost;

    private String genMethodName;

    private List<String> genImportList;

    private String genTestCaseCode;

    private String genTestCaseDesc;

    private Integer genTestCaseNum;

    private String genTestCaseRunner;

    private String genTestPreTemplate;

    private String genTestCaseTemplate;

    private String genTestPostTemplate;

    private String genTestConfigTemplate;

    private Integer genPerformanceTotalThreadNum;

    private Integer genPerformanceSingleThreadExecuteTimes;

    private Integer genPerformanceTotalThreadSetupTime;

    private Integer genTestThreadPoolSize;

    private Integer genTotalInvocationCount;

    private String testClassName;

    private String testFullClassName;

    private String testMethodName;

    private String testMethodParamStr;

    private List<ParamMetaDataModel> testParamList;

    private ReturnMetaDataModel testReturnObject;

    private boolean skipTestGen;

    public String getGenTestCodePath() {
        return genTestCodePath;
    }

    public void setGenTestCodePath(String genTestCodePath) {
        this.genTestCodePath = genTestCodePath;
    }

    public String getGenTestPrePath() {
        return genTestPrePath;
    }

    public void setGenTestPrePath(String genTestPrePath) {
        this.genTestPrePath = genTestPrePath;
    }

    public String getGenTestPostPath() {
        return genTestPostPath;
    }

    public void setGenTestPostPath(String genTestPostPath) {
        this.genTestPostPath = genTestPostPath;
    }

    public String getGenTestDataPath() {
        return genTestDataPath;
    }

    public void setGenTestDataPath(String genTestDataPath) {
        this.genTestDataPath = genTestDataPath;
    }

    public String getGenProjectName() {
        return genProjectName;
    }

    public void setGenProjectName(String genProjectName) {
        this.genProjectName = genProjectName;
    }

    public String getGenProjectPath() {
        return genProjectPath;
    }

    public void setGenProjectPath(String genProjectPath) {
        this.genProjectPath = genProjectPath;
    }

    public String getGenPackageName() {
        return genPackageName;
    }

    public void setGenPackageName(String genPackageName) {
        this.genPackageName = genPackageName;
    }

    public String getGenHttpUrl() {
        return genHttpUrl;
    }

    public void setGenHttpUrl(String genHttpUrl) {
        this.genHttpUrl = genHttpUrl;
    }

    public String getGenClassPre() {
        return genClassPre;
    }

    public void setGenClassPre(String genClassPre) {
        this.genClassPre = genClassPre;
    }

    public String getGenClassName() {
        return genClassName;
    }

    public void setGenClassName(String genClassName) {
        this.genClassName = genClassName;
    }

    public String getGenClassPost() {
        return genClassPost;
    }

    public void setGenClassPost(String genClassPost) {
        this.genClassPost = genClassPost;
    }

    public String getGenMethodName() {
        return genMethodName;
    }

    public void setGenMethodName(String genMethodName) {
        this.genMethodName = genMethodName;
    }

    public void setGenImportList(List<String> genImportList) {
        this.genImportList = genImportList;
    }

    public String getGenTestCaseCode() {
        return genTestCaseCode;
    }

    public void setGenTestCaseCode(String genTestCaseCode) {
        this.genTestCaseCode = genTestCaseCode;
    }

    public String getGenTestCaseDesc() {
        return genTestCaseDesc;
    }

    public void setGenTestCaseDesc(String genTestCaseDesc) {
        this.genTestCaseDesc = genTestCaseDesc;
    }

    public Integer getGenTestCaseNum() {
        return genTestCaseNum;
    }

    public void setGenTestCaseNum(Integer genTestCaseNum) {
        this.genTestCaseNum = genTestCaseNum;
    }

    public String getGenTestCaseRunner() {
        return genTestCaseRunner;
    }

    public void setGenTestCaseRunner(String genTestCaseRunner) {
        this.genTestCaseRunner = genTestCaseRunner;
    }

    public String getGenTestPreTemplate() {
        return genTestPreTemplate;
    }

    public void setGenTestPreTemplate(String genTestPreTemplate) {
        this.genTestPreTemplate = genTestPreTemplate;
    }

    public String getGenTestCaseTemplate() {
        return genTestCaseTemplate;
    }

    public void setGenTestCaseTemplate(String genTestCaseTemplate) {
        this.genTestCaseTemplate = genTestCaseTemplate;
    }

    public String getGenTestPostTemplate() {
        return genTestPostTemplate;
    }

    public void setGenTestPostTemplate(String genTestPostTemplate) {
        this.genTestPostTemplate = genTestPostTemplate;
    }

    public String getGenTestConfigTemplate() {
        return genTestConfigTemplate;
    }

    public void setGenTestConfigTemplate(String genTestConfigTemplate) {
        this.genTestConfigTemplate = genTestConfigTemplate;
    }

    public Integer getGenPerformanceTotalThreadNum() {
        return genPerformanceTotalThreadNum;
    }

    public void setGenPerformanceTotalThreadNum(Integer genPerformanceTotalThreadNum) {
        this.genPerformanceTotalThreadNum = genPerformanceTotalThreadNum;
    }

    public Integer getGenPerformanceSingleThreadExecuteTimes() {
        return genPerformanceSingleThreadExecuteTimes;
    }

    public void setGenPerformanceSingleThreadExecuteTimes(Integer genPerformanceSingleThreadExecuteTimes) {
        this.genPerformanceSingleThreadExecuteTimes = genPerformanceSingleThreadExecuteTimes;
    }

    public Integer getGenPerformanceTotalThreadSetupTime() {
        return genPerformanceTotalThreadSetupTime;
    }

    public void setGenPerformanceTotalThreadSetupTime(Integer genPerformanceTotalThreadSetupTime) {
        this.genPerformanceTotalThreadSetupTime = genPerformanceTotalThreadSetupTime;
    }

    public Integer getGenTestThreadPoolSize() {
        return genTestThreadPoolSize;
    }

    public void setGenTestThreadPoolSize(Integer genTestThreadPoolSize) {
        this.genTestThreadPoolSize = genTestThreadPoolSize;
    }

    public Integer getGenTotalInvocationCount() {
        return genTotalInvocationCount;
    }

    public void setGenTotalInvocationCount(Integer genTotalInvocationCount) {
        this.genTotalInvocationCount = genTotalInvocationCount;
    }

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestFullClassName() {
        return testFullClassName;
    }

    public void setTestFullClassName(String testFullClassName) {
        this.testFullClassName = testFullClassName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getTestMethodParamStr() {
        return testMethodParamStr;
    }

    public void setTestMethodParamStr(String testMethodParamStr) {
        this.testMethodParamStr = testMethodParamStr;
    }

    public void setTestParamList(List<ParamMetaDataModel> testParamList) {
        this.testParamList = testParamList;
    }

    public ReturnMetaDataModel getTestReturnObject() {
        return testReturnObject;
    }

    public void setTestReturnObject(ReturnMetaDataModel testReturnObject) {
        this.testReturnObject = testReturnObject;
    }

    public boolean isSkipTestGen() {
        return skipTestGen;
    }

    public void setSkipTestGen(boolean skipTestGen) {
        this.skipTestGen = skipTestGen;
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

    public List<ParamMetaDataModel> getTestParamList() {
        if (testParamList == null) {
            synchronized (this) {
                if (testParamList == null) {
                    testParamList = new ArrayList<ParamMetaDataModel>();
                }
            }
        }
        return testParamList;
    }

    @Override
    public String toString() {
        return "TestCaseDataModel{" +
                "genTestCodePath='" + genTestCodePath + '\'' +
                ", genTestPrePath='" + genTestPrePath + '\'' +
                ", genTestPostPath='" + genTestPostPath + '\'' +
                ", genTestDataPath='" + genTestDataPath + '\'' +
                ", genProjectName='" + genProjectName + '\'' +
                ", genProjectPath='" + genProjectPath + '\'' +
                ", genPackageName='" + genPackageName + '\'' +
                ", genHttpUrl='" + genHttpUrl + '\'' +
                ", genClassPre='" + genClassPre + '\'' +
                ", genClassName='" + genClassName + '\'' +
                ", genClassPost='" + genClassPost + '\'' +
                ", genMethodName='" + genMethodName + '\'' +
                ", genImportList=" + genImportList +
                ", genTestCaseCode='" + genTestCaseCode + '\'' +
                ", genTestCaseDesc='" + genTestCaseDesc + '\'' +
                ", genTestCaseNum=" + genTestCaseNum +
                ", genTestCaseRunner='" + genTestCaseRunner + '\'' +
                ", genTestPreTemplate='" + genTestPreTemplate + '\'' +
                ", genTestCaseTemplate='" + genTestCaseTemplate + '\'' +
                ", genTestPostTemplate='" + genTestPostTemplate + '\'' +
                ", genTestConfigTemplate='" + genTestConfigTemplate + '\'' +
                ", genPerformanceTotalThreadNum=" + genPerformanceTotalThreadNum +
                ", genPerformanceSingleThreadExecuteTimes=" + genPerformanceSingleThreadExecuteTimes +
                ", genPerformanceTotalThreadSetupTime=" + genPerformanceTotalThreadSetupTime +
                ", genTestThreadPoolSize=" + genTestThreadPoolSize +
                ", genTotalInvocationCount=" + genTotalInvocationCount +
                ", testClassName='" + testClassName + '\'' +
                ", testFullClassName='" + testFullClassName + '\'' +
                ", testMethodName='" + testMethodName + '\'' +
                ", testMethodParamStr='" + testMethodParamStr + '\'' +
                ", testParamList=" + testParamList +
                ", testReturnObject=" + testReturnObject +
                ", skipTestGen=" + skipTestGen +
                '}';
    }
}
