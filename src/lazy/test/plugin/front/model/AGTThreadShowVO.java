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
public class AGTThreadShowVO extends AGTAbstractShowVO {

    private String genThreadClassName;

    private String genThreadMethodName;

    private String genThreadTestNum;

    private String genThreadTemplate;

    private String genThreadTestRunner;

    private String genThreadPoolSize;

    private String genTotalInvokeCount;
    
    private IFType ifType;

    public String getGenThreadClassName() {
        return genThreadClassName;
    }

    public void setGenThreadClassName(String genThreadClassName) {
        this.genThreadClassName = genThreadClassName;
    }

    public String getGenThreadMethodName() {
        return genThreadMethodName;
    }

    public void setGenThreadMethodName(String genThreadMethodName) {
        this.genThreadMethodName = genThreadMethodName;
    }

    public String getGenThreadTestNum() {
        return genThreadTestNum;
    }

    public void setGenThreadTestNum(String genThreadTestNum) {
        this.genThreadTestNum = genThreadTestNum;
    }

    public String getGenThreadTemplate() {
        return genThreadTemplate;
    }

    public void setGenThreadTemplate(String genThreadTemplate) {
        this.genThreadTemplate = genThreadTemplate;
    }

    public String getGenThreadTestRunner() {
        return genThreadTestRunner;
    }

    public void setGenThreadTestRunner(String genThreadTestRunner) {
        this.genThreadTestRunner = genThreadTestRunner;
    }

    public String getGenThreadPoolSize() {
        return genThreadPoolSize;
    }

    public void setGenThreadPoolSize(String genThreadPoolSize) {
        this.genThreadPoolSize = genThreadPoolSize;
    }

    public String getGenTotalInvokeCount() {
        return genTotalInvokeCount;
    }

    public void setGenTotalInvokeCount(String genTotalInvokeCount) {
        this.genTotalInvokeCount = genTotalInvokeCount;
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
                && StringUtils.isNotBlank(genResourceName) && genResourceName.matches("^\\w+[\\.\\w+\\.]+\\w+$")
                && StringUtils.isNotBlank(genThreadClassName) && StringUtils.isNotBlank(genThreadMethodName) && StringUtils.isNotBlank(genThreadTestNum)
                && StringUtils.isNotBlank(genThreadTemplate) && StringUtils.isNotBlank(genThreadTestRunner)
                && StringUtils.isNotBlank(genThreadPoolSize) && StringUtils.isNotBlank(genTotalInvokeCount)
                && Integer.valueOf(genThreadPoolSize) >= Integer.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD))
                && Integer.valueOf(genThreadPoolSize) <= Integer.valueOf(genTotalInvokeCount) && !checkGenClassExist(genThreadClassName);
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
        return "AGTThreadShowVO{" +
                "genThreadClassName='" + genThreadClassName + '\'' +
                ", genThreadMethodName='" + genThreadMethodName + '\'' +
                ", genThreadTestNum='" + genThreadTestNum + '\'' +
                ", genThreadTemplate='" + genThreadTemplate + '\'' +
                ", genThreadTestRunner='" + genThreadTestRunner + '\'' +
                ", genThreadPoolSize='" + genThreadPoolSize + '\'' +
                ", genTotalInvokeCount='" + genTotalInvokeCount + '\'' +
                ", ifType='" + ifType + '\'' +
                "} " + super.toString();
    }
}
