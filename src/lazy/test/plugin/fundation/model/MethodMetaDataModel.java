package lazy.test.plugin.fundation.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class MethodMetaDataModel {

    private String methodSimpleName;

    private String methodQualifiedName;

    private List<ParamMetaDataModel> paramList;

    private ReturnMetaDataModel returnObject;

    public String getMethodSimpleName() {
        return methodSimpleName;
    }

    public void setMethodSimpleName(String methodSimpleName) {
        this.methodSimpleName = StringUtils.strip(methodSimpleName);
    }

    public String getMethodQualifiedName() {
        return methodQualifiedName;
    }

    public void setMethodQualifiedName(String methodQualifiedName) {
        this.methodQualifiedName = StringUtils.strip(methodQualifiedName);
    }

    public void setParamList(List<ParamMetaDataModel> paramList) {
        this.paramList = paramList;
    }

    public ReturnMetaDataModel getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(ReturnMetaDataModel returnObject) {
        this.returnObject = returnObject;
    }

    public List<ParamMetaDataModel> getParamList() {
        if (paramList == null) {
            synchronized (this) {
                if (paramList == null) {
                    paramList = new ArrayList<ParamMetaDataModel>();
                }
            }
        }

        return paramList;
    }

    @Override
    public String toString() {
        return "MethodMetaDataModel{" +
                "methodSimpleName='" + methodSimpleName + '\'' +
                ", methodQualifiedName='" + methodQualifiedName + '\'' +
                ", paramList=" + paramList +
                ", returnObject=" + returnObject +
                '}';
    }
}
