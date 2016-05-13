package lazy.test.plugin.front.model;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class AGTPerformanceShowVO extends AGTAbstractShowVO {

    private String genPerformanceClassPre;

    private String genPerformanceClassName;

    private String genPerformanceClassPost;

    private String genPerformancePreTemplate;

    private String genPerformanceClassTemplate;

    private String genPerformancePostTemplate;

    private String genPerformanceConfigTemplate;

    private String genPerformanceTestRunner;

    private String genPerformanceTotalThreadNum;

    private String genPerformanceTotalThreadSetupTime;

    private String genPerformanceSingleThreadExecuteTimes;
    
    private IFType ifType;

    public String getGenPerformanceClassPre() {
        return genPerformanceClassPre;
    }

    public void setGenPerformanceClassPre(String genPerformanceClassPre) {
        this.genPerformanceClassPre = genPerformanceClassPre;
    }

    public String getGenPerformanceClassName() {
        return genPerformanceClassName;
    }

    public void setGenPerformanceClassName(String genPerformanceClassName) {
        this.genPerformanceClassName = genPerformanceClassName;
    }

    public String getGenPerformanceClassPost() {
        return genPerformanceClassPost;
    }

    public void setGenPerformanceClassPost(String genPerformanceClassPost) {
        this.genPerformanceClassPost = genPerformanceClassPost;
    }

    public String getGenPerformancePreTemplate() {
        return genPerformancePreTemplate;
    }

    public void setGenPerformancePreTemplate(String genPerformancePreTemplate) {
        this.genPerformancePreTemplate = genPerformancePreTemplate;
    }

    public String getGenPerformanceClassTemplate() {
        return genPerformanceClassTemplate;
    }

    public void setGenPerformanceClassTemplate(String genPerformanceClassTemplate) {
        this.genPerformanceClassTemplate = genPerformanceClassTemplate;
    }

    public String getGenPerformancePostTemplate() {
        return genPerformancePostTemplate;
    }

    public void setGenPerformancePostTemplate(String genPerformancePostTemplate) {
        this.genPerformancePostTemplate = genPerformancePostTemplate;
    }

    public String getGenPerformanceConfigTemplate() {
        return genPerformanceConfigTemplate;
    }

    public void setGenPerformanceConfigTemplate(String genPerformanceConfigTemplate) {
        this.genPerformanceConfigTemplate = genPerformanceConfigTemplate;
    }

    public String getGenPerformanceTestRunner() {
        return genPerformanceTestRunner;
    }

    public void setGenPerformanceTestRunner(String genPerformanceTestRunner) {
        this.genPerformanceTestRunner = genPerformanceTestRunner;
    }

    public String getGenPerformanceTotalThreadNum() {
        return genPerformanceTotalThreadNum;
    }

    public void setGenPerformanceTotalThreadNum(String genPerformanceTotalThreadNum) {
        this.genPerformanceTotalThreadNum = genPerformanceTotalThreadNum;
    }

    public String getGenPerformanceTotalThreadSetupTime() {
        return genPerformanceTotalThreadSetupTime;
    }

    public void setGenPerformanceTotalThreadSetupTime(String genPerformanceTotalThreadSetupTime) {
        this.genPerformanceTotalThreadSetupTime = genPerformanceTotalThreadSetupTime;
    }

    public String getGenPerformanceSingleThreadExecuteTimes() {
        return genPerformanceSingleThreadExecuteTimes;
    }

    public void setGenPerformanceSingleThreadExecuteTimes(String genPerformanceSingleThreadExecuteTimes) {
        this.genPerformanceSingleThreadExecuteTimes = genPerformanceSingleThreadExecuteTimes;
    }

    public IFType getIfType() {
		return ifType;
	}

	public void setIfType(IFType ifType) {
		this.ifType = ifType;
	}

	@Override
    protected boolean validSpecial() {
        return StringUtils.isNotBlank(tarClassName) && StringUtils.isNotBlank(tarMethodName)
                && StringUtils.isNotBlank(genPerformanceClassPre) && StringUtils.isNotBlank(genPerformanceClassName) && StringUtils.isNotBlank(genPerformanceClassPost)
                && StringUtils.isNotBlank(genPerformancePreTemplate) && StringUtils.isNotBlank(genPerformancePostTemplate)
                && StringUtils.isNotBlank(genPerformanceClassTemplate) && StringUtils.isNotBlank(genPerformanceConfigTemplate)
                && StringUtils.isNotBlank(genPerformanceTestRunner) && StringUtils.isNotBlank(genPerformanceTotalThreadNum)
                && StringUtils.isNotBlank(genPerformanceTotalThreadSetupTime) && StringUtils.isNotBlank(genPerformanceSingleThreadExecuteTimes)
                && StringUtils.isNumeric(genPerformanceTotalThreadSetupTime) && StringUtils.isNumeric(genPerformanceTotalThreadNum) && StringUtils.isNumeric(genPerformanceSingleThreadExecuteTimes)
                && Integer.valueOf(genPerformanceTotalThreadNum) >= Integer.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD))
                && !checkGenClassExist(genPerformanceClassPre) && !checkGenClassExist(genPerformanceClassName) && !checkGenClassExist(genPerformanceClassPost);
    }


    private boolean checkGenClassExist(String genClassName) {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(tarProjectName);
        StringBuffer sbf = new StringBuffer();
        sbf.append("/" + AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        sbf.append(genPackageName.replaceAll("\\.", "/"));
        sbf.append("/" + genClassName + ".java");

        IFile file = project.getFile(sbf.toString());
        return file.exists();
    }

    @Override
    public String toString() {
        return "AGTPerformanceShowVO{" +
                "genPerformanceClassPre='" + genPerformanceClassPre + '\'' +
                ", genPerformanceClassName='" + genPerformanceClassName + '\'' +
                ", genPerformanceClassPost='" + genPerformanceClassPost + '\'' +
                ", genPerformancePreTemplate='" + genPerformancePreTemplate + '\'' +
                ", genPerformanceClassTemplate='" + genPerformanceClassTemplate + '\'' +
                ", genPerformancePostTemplate='" + genPerformancePostTemplate + '\'' +
                ", genPerformanceConfigTemplate='" + genPerformanceConfigTemplate + '\'' +
                ", genPerformanceTestRunner='" + genPerformanceTestRunner + '\'' +
                ", genPerformanceTotalThreadNum='" + genPerformanceTotalThreadNum + '\'' +
                ", genPerformanceTotalThreadSetupTime='" + genPerformanceTotalThreadSetupTime + '\'' +
                ", genPerformanceSingleThreadExecuteTimes='" + genPerformanceSingleThreadExecuteTimes + '\'' +
                ", ifType='" + ifType + '\'' +
                "} " + super.toString();
    }
}
