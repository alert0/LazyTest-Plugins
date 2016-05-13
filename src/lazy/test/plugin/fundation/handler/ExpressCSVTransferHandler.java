package lazy.test.plugin.fundation.handler;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.model.ParamMetaDataModel;
import lazy.test.plugin.fundation.model.TestCaseDataModel;
import lazy.test.plugin.fundation.model.TestResDataModel;

/**
 * Created by wyzhouqiang on 2016/01/05.
 */
public class ExpressCSVTransferHandler {

    public static List<TestResDataModel[]> generateTestData(TestCaseDataModel testCaseDataModel) {
        List<TestResDataModel[]> rowList = new ArrayList<TestResDataModel[]>();
        List<TestResDataModel> headRow = genHeadColumnList();
        rowList.add(headRow.toArray(new TestResDataModel[1]));

        int testNum = testCaseDataModel.getGenTestCaseNum();
        int paramNum = testCaseDataModel.getTestParamList().isEmpty() ? 0 : testCaseDataModel.getTestParamList().size();
        if (testNum < paramNum) {
            testNum = paramNum;
        }
        for (int i = 1; i <= testNum; i++) {
            List<TestResDataModel> dataRow = genDataColumnList(testCaseDataModel, i);
            rowList.add(dataRow.toArray(new TestResDataModel[1]));
        }

        return rowList;
    }

    private static List<TestResDataModel> genHeadColumnList(){
        List<TestResDataModel> list = new ArrayList<TestResDataModel>();
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_CLASS_NAME));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_METHOD_NAME));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_PARAM_NAME));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_BEAN_ID));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_DEFAULT_KEY));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_DEFAULT_VALUE));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_RUN_FLAG));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_SET_KEY));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_SET_VALUE));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_RESULT_KEY));
        list.add(new TestResDataModel(AGTCommonProperties.TEST_DATA_EXPRESS_HEAD_RESULT_VALUE));
        return list;
    }

    private static List<TestResDataModel> genDataColumnList(TestCaseDataModel model, int rowNum) {
        List<TestResDataModel> list = new ArrayList<TestResDataModel>();
        //className, methodName
        if (rowNum == 1) {
            list.add(new TestResDataModel(model.getTestFullClassName()));
            list.add(new TestResDataModel(model.getTestMethodName()));
        } else {
            list.add(new TestResDataModel(""));
            list.add(new TestResDataModel(""));
        }
        //paramName
        List<ParamMetaDataModel> paramList = model.getTestParamList();
        if (paramList.size() >= rowNum) {
            ParamMetaDataModel param = paramList.get(rowNum - 1);
            list.add(new TestResDataModel(param.getParamStr() + "_{" + param.getParamName() + "}"));
        } else {
            list.add(new TestResDataModel(""));
        }
        //beanId
        if (rowNum == 1) {
            list.add(new TestResDataModel(StringUtils.uncapitalize(model.getTestClassName())));
        } else {
            list.add(new TestResDataModel(""));
        }

        //defaultKey
        list.add(new TestResDataModel(""));
        list.add(new TestResDataModel(""));
        //runFlag
        if (model.getGenTestCaseNum() >= rowNum) {
            list.add(new TestResDataModel("1"));
        } else {
            list.add(new TestResDataModel(""));
        }
        //setKey
        list.add(new TestResDataModel(""));
        list.add(new TestResDataModel(""));
        //resultKey
        list.add(new TestResDataModel(""));
        list.add(new TestResDataModel(""));

        return list;
    }
}
