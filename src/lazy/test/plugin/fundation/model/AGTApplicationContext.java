package lazy.test.plugin.fundation.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazy.test.plugin.front.model.AGTAbstractShowVO;

/**
 * Created by wyzhouqiang on 2015/12/2.
 */
public class AGTApplicationContext {

    private ClassMetaDataModel classMetaDataModel;

    private AGTAbstractShowVO vo;

    private List<TestCaseDataModel> testCaseDataModelList;

    
    private TestCaseDataModel testJsfConfigDataModel;

	private Map<String, Object> userMap;

    public ClassMetaDataModel getClassMetaDataModel() {
        return classMetaDataModel;
    }

    public void setClassMetaDataModel(ClassMetaDataModel classMetaDataModel) {
        this.classMetaDataModel = classMetaDataModel;
    }

    public void setVo(AGTAbstractShowVO vo) {
        this.vo = vo;
    }

    public void setTestCaseDataModelList(List<TestCaseDataModel> testCaseDataModelList) {
        this.testCaseDataModelList = testCaseDataModelList;
    }

    public void setUserMap(Map<String, Object> userMap) {
        this.userMap = userMap;
    }

    public Map<String, Object> getUserMap() {
        if (userMap == null) {
            synchronized (this) {
                if (userMap == null) {
                    userMap = new HashMap<String, Object>();
                }
            }
        }
        return userMap;
    }

    public List<TestCaseDataModel> getTestCaseDataModelList() {
        if (testCaseDataModelList == null) {
            synchronized (this) {
                if (testCaseDataModelList == null) {
                    testCaseDataModelList = new ArrayList<TestCaseDataModel>();
                }
            }
        }
        return testCaseDataModelList;
    }

    public <T extends AGTAbstractShowVO> T getVo() {
        return (T)vo;
    }

    public void putUserData(String key, Object value) {
        this.getUserMap().put(key, value);
    }

    public <T> T getUserData(String key) {
        return (T)this.getUserMap().get(key);
    }

    @Override
    public String toString() {
        return "AGTApplicationContext{" +
                "classMetaDataModel=" + classMetaDataModel +
                ", vo=" + vo +
                ", testCaseDataModelList=" + testCaseDataModelList +
                ", testJsfConfigDataModel=" + testJsfConfigDataModel +
                ", userMap=" + userMap +
                '}';
    }
}
