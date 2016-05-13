package lazy.test.plugin.fundation.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class ReturnMetaDataModel {

    private String returnStr;

    /**
     * 解决泛型化对象, 如Map<String, String>
     * key:     java.util.Map
     * value:   java.lang.String, java.lang.String
     */
    private Map<String, Object> returnMap;

    public String getReturnStr() {
        return returnStr;
    }

    public void setReturnStr(String returnStr) {
        this.returnStr = returnStr;
    }

    public void setReturnMap(Map<String, Object> returnMap) {
        this.returnMap = returnMap;
    }

    public Map<String, Object> getReturnMap() {
        if (returnMap == null) {
            synchronized (this) {
                if (returnMap == null) {
                    returnMap = new HashMap<String, Object>();
                }
            }
        }

        return returnMap;
    }

    @Override
    public String toString() {
        return "ReturnMetaDataModel{" +
                "returnStr='" + returnStr + '\'' +
                ", returnMap=" + returnMap +
                '}';
    }
}
