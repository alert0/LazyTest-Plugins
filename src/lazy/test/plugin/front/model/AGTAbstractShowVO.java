package lazy.test.plugin.front.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wyzhouqiang on 2016/1/20.
 */
public abstract class AGTAbstractShowVO {

	public enum IFType { DUBBO, JSF };

    protected String tarProjectName;

    protected String tarProjectPath;

    protected String tarClassName;

    protected String tarMethodName;

    protected String genPackageName;

    protected String genResourceName;

    public String getTarProjectName() {
        return tarProjectName;
    }

    public void setTarProjectName(String tarProjectName) {
        this.tarProjectName = tarProjectName;
    }

    public String getTarProjectPath() {
        return tarProjectPath;
    }

    public void setTarProjectPath(String tarProjectPath) {
        this.tarProjectPath = tarProjectPath;
    }

    public String getTarClassName() {
        return tarClassName;
    }

    public void setTarClassName(String tarClassName) {
        this.tarClassName = tarClassName;
    }

    public String getTarMethodName() {
        return tarMethodName;
    }

    public void setTarMethodName(String tarMethodName) {
        this.tarMethodName = tarMethodName;
    }

    public String getGenPackageName() {
        return genPackageName;
    }

    public void setGenPackageName(String genPackageName) {
        this.genPackageName = genPackageName;
    }

    public String getGenResourceName() {
        return genResourceName;
    }

    public void setGenResourceName(String genResourceName) {
        this.genResourceName = genResourceName;
    }

    public boolean validate() {
        return StringUtils.isNotBlank(tarProjectName) && StringUtils.isNotBlank(tarProjectPath)
                && StringUtils.isNotBlank(genPackageName) && genPackageName.matches("^\\w+[\\.\\w+\\.]+\\w+$")
                && validSpecial();
    }

    protected abstract boolean validSpecial();

    @Override
    public String toString() {
        return "AGTAbstractShowVO{" +
                "tarProjectName='" + tarProjectName + '\'' +
                ", tarProjectPath='" + tarProjectPath + '\'' +
                ", tarClassName='" + tarClassName + '\'' +
                ", tarMethodName='" + tarMethodName + '\'' +
                ", genPackageName='" + genPackageName + '\'' +
                ", genResourceName='" + genResourceName + '\'' +
                '}';
    }
}
