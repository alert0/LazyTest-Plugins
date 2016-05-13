package lazy.test.plugin.fundation.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyzhouqiang on 2015/11/30.
 */
public class TestResDataModel {

    private Map<String, Object> resDataTypeMap;

    private String resDataValue;

    private String resDataPath;

    private boolean needGenInput;

    public TestResDataModel() {
    }

    public TestResDataModel(String resDataValue) {
        this.resDataValue = resDataValue;
    }

    public void setResDataTypeMap(Map<String, Object> resDataTypeMap) {
        this.resDataTypeMap = resDataTypeMap;
    }

    public String getResDataValue() {
        return resDataValue;
    }

    public void setResDataValue(String resDataValue) {
        this.resDataValue = resDataValue;
    }

    public String getResDataPath() {
        return resDataPath;
    }

    public void setResDataPath(String resDataPath) {
        this.resDataPath = resDataPath;
    }

    public boolean isNeedGenInput() {
        return needGenInput;
    }

    public void setNeedGenInput(boolean needGenInput) {
        this.needGenInput = needGenInput;
    }

    public Map<String, Object> getResDataTypeMap() {
        if (resDataTypeMap == null) {
            synchronized (this) {
                if (resDataTypeMap == null) {
                    resDataTypeMap = new HashMap<String, Object>();
                }
            }
        }

        return resDataTypeMap;
    }

    @Override
    public String toString() {
        return "TestResDataModel{" +
                "resDataTypeMap=" + resDataTypeMap +
                ", resDataValue='" + resDataValue + '\'' +
                ", resDataPath='" + resDataPath + '\'' +
                ", needGenInput=" + needGenInput +
                '}';
    }
}
