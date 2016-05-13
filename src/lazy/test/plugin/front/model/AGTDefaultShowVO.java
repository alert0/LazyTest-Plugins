package lazy.test.plugin.front.model;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

import java.io.File;
import java.util.Arrays;

import lazy.test.plugin.fundation.common.AGTCommonProperties;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class AGTDefaultShowVO extends AGTAbstractShowVO {
	
	private String genNormalClassName;

    private String genNormalMethodName;

    private String genNormalTestNum;

    private String genNormalTemplate;

    private String genNormalTestRunner;

    private String genExceptionClassName;

    private String genExceptionMethodName;

    private String genExceptionTestNum;

    private String genExceptionTemplate;

    private String genExceptionTestRunner;

    private String genParamClassName;

    private String genParamMethodName;

    private String genParamTestNum;

    private String genParamTemplate;

    private String genParamTestRunner;

    private boolean skipGenNormal;

    private boolean skipGenException;

    private boolean skipGenParam;
    
    private IFType ifType;

	private File[] customerTemplateList;

    public String getGenNormalClassName() {
        return genNormalClassName;
    }

    public void setGenNormalClassName(String genNormalClassName) {
        this.genNormalClassName = genNormalClassName;
    }

    public String getGenParamClassName() {
        return genParamClassName;
    }

    public void setGenParamClassName(String genParamClassName) {
        this.genParamClassName = genParamClassName;
    }

    public String getGenExceptionClassName() {
        return genExceptionClassName;
    }

    public void setGenExceptionClassName(String genExceptionClassName) {
        this.genExceptionClassName = genExceptionClassName;
    }

    public String getGenNormalMethodName() {
        return genNormalMethodName;
    }

    public void setGenNormalMethodName(String genNormalMethodName) {
        this.genNormalMethodName = genNormalMethodName;
    }

    public String getGenParamMethodName() {
        return genParamMethodName;
    }

    public void setGenParamMethodName(String genParamMethodName) {
        this.genParamMethodName = genParamMethodName;
    }

    public String getGenExceptionMethodName() {
        return genExceptionMethodName;
    }

    public void setGenExceptionMethodName(String genExceptionMethodName) {
        this.genExceptionMethodName = genExceptionMethodName;
    }

    public String getGenNormalTestNum() {
        return genNormalTestNum;
    }

    public void setGenNormalTestNum(String genNormalTestNum) {
        this.genNormalTestNum = genNormalTestNum;
    }

    public String getGenParamTestNum() {
        return genParamTestNum;
    }

    public void setGenParamTestNum(String genParamTestNum) {
        this.genParamTestNum = genParamTestNum;
    }

    public String getGenExceptionTestNum() {
        return genExceptionTestNum;
    }

    public void setGenExceptionTestNum(String genExceptionTestNum) {
        this.genExceptionTestNum = genExceptionTestNum;
    }

    public String getGenNormalTemplate() {
        return genNormalTemplate;
    }

    public void setGenNormalTemplate(String genNormalTemplate) {
        this.genNormalTemplate = genNormalTemplate;
    }

    public String getGenParamTemplate() {
        return genParamTemplate;
    }

    public void setGenParamTemplate(String genParamTemplate) {
        this.genParamTemplate = genParamTemplate;
    }

    public String getGenExceptionTemplate() {
        return genExceptionTemplate;
    }

    public void setGenExceptionTemplate(String genExceptionTemplate) {
        this.genExceptionTemplate = genExceptionTemplate;
    }

    public String getGenNormalTestRunner() {
        return genNormalTestRunner;
    }

    public void setGenNormalTestRunner(String genNormalTestRunner) {
        this.genNormalTestRunner = genNormalTestRunner;
    }

    public String getGenExceptionTestRunner() {
        return genExceptionTestRunner;
    }

    public void setGenExceptionTestRunner(String genExceptionTestRunner) {
        this.genExceptionTestRunner = genExceptionTestRunner;
    }

    public String getGenParamTestRunner() {
        return genParamTestRunner;
    }

    public void setGenParamTestRunner(String genParamTestRunner) {
        this.genParamTestRunner = genParamTestRunner;
    }

    public boolean isSkipGenNormal() {
        return skipGenNormal;
    }

    public void setSkipGenNormal(boolean skipGenNormal) {
        this.skipGenNormal = skipGenNormal;
    }

    public boolean isSkipGenException() {
        return skipGenException;
    }

    public void setSkipGenException(boolean skipGenException) {
        this.skipGenException = skipGenException;
    }

    public boolean isSkipGenParam() {
        return skipGenParam;
    }

    public void setSkipGenParam(boolean skipGenParam) {
        this.skipGenParam = skipGenParam;
    }

    public IFType getIfType() {
		return ifType;
	}

	public void setIfType(IFType ifType) {
		this.ifType = ifType;
	}

    public File[] getCustomerTemplateList() {
        return customerTemplateList;
    }

    public void setCustomerTemplateList(File[] customerTemplateList) {
        this.customerTemplateList = customerTemplateList;
    }

    @Override
    protected boolean validSpecial() {
        return normalValid() && exceptionValid() && paramValid()
                && StringUtils.isNotBlank(tarClassName) && StringUtils.isNotBlank(tarMethodName)
                && StringUtils.isNotBlank(genResourceName) && genResourceName.matches("^\\w+[\\.\\w+\\.]+\\w+$")
                && !checkGenClassExist(genNormalClassName) && !checkGenClassExist(genExceptionClassName) && !checkGenClassExist(genParamClassName);
    }

    private boolean normalValid() {
        if (skipGenNormal) {
            return true;
        }
        return StringUtils.isNotBlank(genNormalClassName) && StringUtils.isNotBlank(genNormalMethodName)
                && StringUtils.isNotBlank(genNormalTestNum) && StringUtils.isNumeric(genNormalTestNum) && StringUtils.isNotBlank(genNormalTemplate);
    }

    private boolean exceptionValid() {
        if (skipGenException) {
            return true;
        }
        return StringUtils.isNotBlank(genExceptionClassName) && StringUtils.isNotBlank(genExceptionMethodName)
                && StringUtils.isNotBlank(genExceptionTestNum) && StringUtils.isNumeric(genExceptionTestNum) && StringUtils.isNotBlank(genExceptionTemplate);
    }

    private boolean paramValid() {
        if (skipGenParam) {
            return true;
        }
        return StringUtils.isNotBlank(genParamClassName) && StringUtils.isNotBlank(genParamMethodName)
                && StringUtils.isNotBlank(genParamTestNum) && StringUtils.isNumeric(genParamTestNum) && StringUtils.isNotBlank(genParamTemplate);
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
        return "AGTDefaultShowVO{" +
                "genNormalClassName='" + genNormalClassName + '\'' +
                ", genNormalMethodName='" + genNormalMethodName + '\'' +
                ", genNormalTestNum='" + genNormalTestNum + '\'' +
                ", genNormalTemplate='" + genNormalTemplate + '\'' +
                ", genNormalTestRunner='" + genNormalTestRunner + '\'' +
                ", genExceptionClassName='" + genExceptionClassName + '\'' +
                ", genExceptionMethodName='" + genExceptionMethodName + '\'' +
                ", genExceptionTestNum='" + genExceptionTestNum + '\'' +
                ", genExceptionTemplate='" + genExceptionTemplate + '\'' +
                ", genExceptionTestRunner='" + genExceptionTestRunner + '\'' +
                ", genParamClassName='" + genParamClassName + '\'' +
                ", genParamMethodName='" + genParamMethodName + '\'' +
                ", genParamTestNum='" + genParamTestNum + '\'' +
                ", genParamTemplate='" + genParamTemplate + '\'' +
                ", genParamTestRunner='" + genParamTestRunner + '\'' +
                ", skipGenNormal=" + skipGenNormal +
                ", skipGenException=" + skipGenException +
                ", skipGenParam=" + skipGenParam +
                ", ifType=" + ifType +
                ", customerTemplateList=" + Arrays.toString(customerTemplateList) +
                "} " + super.toString();
    }
}
