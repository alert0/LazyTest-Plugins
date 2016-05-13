package lazy.test.plugin.fundation.handler;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.model.ParamMetaDataModel;
import lazy.test.plugin.fundation.model.TestCaseDataModel;
import lazy.test.plugin.fundation.model.TestResDataModel;

/**
 * Created by wyzhouqiang on 2015/11/30.
 */
public class DefaultCSVTransferHandler {

    public static List<TestResDataModel[]> generateTestData(TestCaseDataModel testCaseDataModel, ClassLoader loader) {
        List<TestResDataModel[]> rowList = new ArrayList<TestResDataModel[]>();
        List<TestResDataModel> headRow = genHeadColumnList(testCaseDataModel);
        rowList.add(headRow.toArray(new TestResDataModel[1]));

        for (int i = 1; i <= testCaseDataModel.getGenTestCaseNum(); i++) {
            String testCode = testCaseDataModel.getGenTestCaseCode() + "_" + i;
            List<TestResDataModel> dataRow = genDataColumnList(testCode, testCaseDataModel);
            rowList.add(dataRow.toArray(new TestResDataModel[1]));
        }

        return rowList;
    }

    private static List<TestResDataModel> genHeadColumnList(TestCaseDataModel testCaseDataModel){
        List<TestResDataModel> list = new ArrayList<TestResDataModel>();
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_DEFAULT_HEAD_TEST_RUN));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_DEFAULT_HEAD_TEST_CODE));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_DEFAULT_HEAD_TEST_DESC));
        for (ParamMetaDataModel param : testCaseDataModel.getTestParamList()) {
            Map<String, Object> map = param.getParamMap();
            if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_PRIMITIVE)) {
                String paramType = MessageMappingHandler.findMappingResult(param.getParamStr());
                list.add(new TestResDataModel(paramType + "_{" + param.getParamName() + "}"));
            } else if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE) && param.getParamStr().startsWith("java.lang")) {
                list.add(new TestResDataModel(param.getParamStr() + "_{" + param.getParamName() + "}"));
            } else {
                String paramType = ClassTransferHandler.shortGenericClassName(param.getParamStr());
                list.add(new TestResDataModel(paramType + "_{" + param.getParamName() + "}"));
            }
        }
        return list;
    }

    private static List<TestResDataModel> genDataColumnList(String testCode, TestCaseDataModel testCaseDataModel) {
        List<TestResDataModel> list = new ArrayList<TestResDataModel>();
        list.add(new TestResDataModel("1"));
        list.add(new TestResDataModel(testCode));
        list.add(new TestResDataModel(""));

        for (ParamMetaDataModel param : testCaseDataModel.getTestParamList()) {
            Map<String, Object> map = param.getParamMap();
            if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_PRIMITIVE)) {
                list.add(new TestResDataModel(""));
            } else if (map.containsKey(AGTCommonProperties.AST_PARSE_TYPE_SIMPLE) && param.getParamStr().startsWith("java.lang")) {
                list.add(new TestResDataModel(""));
            } else {
                TestResDataModel resData = new TestResDataModel();
                resData.setNeedGenInput(true);
                resData.setResDataValue(param.getParamName() + "_" + testCode + ".xml");
                String pathDir = StringUtils.substringBeforeLast(testCaseDataModel.getGenTestDataPath(), "/");
                resData.setResDataPath(pathDir + "/" + testCode);
                Map<String, Object> coreMap = new HashMap<String, Object>();
                coreMap.put(AGTCommonProperties.AST_PARSE_KEY_RAW_STR, param.getParamStr());
                coreMap.putAll(param.getParamMap());
                resData.setResDataTypeMap(coreMap);
                list.add(resData);
            }
        }

        return list;
    }
}
