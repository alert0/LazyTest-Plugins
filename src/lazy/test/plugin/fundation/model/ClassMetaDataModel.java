package lazy.test.plugin.fundation.model;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by wyzhouqiang on 2015/11/21.
 */
public class ClassMetaDataModel {

    private String packageQualifiedName;

    private String classSimpleName;

    private String classQualifiedName;

    private MethodMetaDataModel methodMetaDataModel;

    public String getPackageQualifiedName() {
        return packageQualifiedName;
    }

    public void setPackageQualifiedName(String packageQualifiedName) {
        this.packageQualifiedName = StringUtils.strip(packageQualifiedName);
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }

    public void setClassSimpleName(String classSimpleName) {
        this.classSimpleName = StringUtils.strip(classSimpleName);
    }

    public String getClassQualifiedName() {
        return classQualifiedName;
    }

    public void setClassQualifiedName(String classQualifiedName) {
        this.classQualifiedName = StringUtils.strip(classQualifiedName);
    }

    public MethodMetaDataModel getMethodMetaDataModel() {
        return methodMetaDataModel;
    }

    public void setMethodMetaDataModel(MethodMetaDataModel methodMetaDataModel) {
        this.methodMetaDataModel = methodMetaDataModel;
    }

    @Override
    public String toString() {
        return "ClassMetaDataModel{" +
                "packageQualifiedName='" + packageQualifiedName + '\'' +
                ", classSimpleName='" + classSimpleName + '\'' +
                ", classQualifiedName='" + classQualifiedName + '\'' +
                ", methodMetaDataModel=" + methodMetaDataModel +
                '}';
    }
}
