package lazy.test.plugin.fundation.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class ParamMetaDataModel {

    private String paramName;

    private String paramStr;

    /**
     * 解决泛型化对象, 如Map<String, String>
     * key:     java.util.Map
     * value:   java.lang.String, java.lang.String
     */
    private Map<String, Object> paramMap;

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = StringUtils.strip(paramName);
    }

    public String getParamStr() {
        return paramStr;
    }

    public void setParamStr(String paramStr) {
        this.paramStr = paramStr;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Map<String, Object> getParamMap() {
        if (paramMap == null) {
            synchronized (this) {
                if (paramMap == null) {
                    paramMap = new HashMap<String, Object>();
                }
            }
        }

        return paramMap;
    }

    @Override
    public String toString() {
        return "ParamMetaDataModel{" +
                "paramName='" + paramName + '\'' +
                ", paramStr='" + paramStr + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }
}
