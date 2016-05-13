package lazy.test.plugin.fundation.processor;

import java.lang.reflect.Constructor;

import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.model.TestCaseDataModel;
import lazy.test.plugin.fundation.runner.AGTRunner;
import lazy.test.plugin.fundation.runner.AGTRunnerSuit;

/**
 * Created by wyzhouqiang on 2015/11/30.
 */
public class TestCaseGenerateProcessor {

    public static void genTestCaseInfo(AGTApplicationContext context) {
        AGTRunnerSuit suit = new AGTRunnerSuit(context);

        if (context.getTestCaseDataModelList().isEmpty()) {
            return;
        }
        for (TestCaseDataModel model : context.getTestCaseDataModelList()) {
            if (model == null || model.isSkipTestGen()) {
                continue;
            }
            suit.addRunner(genTestCaseRunner(model));
        }
        
//        AGTDefaultShowVO agtDefaultShowVO = null;
//        AGTThreadShowVO agtThreadShowVO = null;
//        AGTPerformanceShowVO agtPerformanceShowVO = null;
//        
//        try {
//        	agtDefaultShowVO = context.getVo();
//        } catch (Exception e) {
//        	
//        }
//        
//        try {
//        	agtThreadShowVO = context.getVo();
//        } catch (Exception e) {
//        	
//        }
//        
//        try {
//        	agtPerformanceShowVO = context.getVo();
//        } catch (Exception e) {
//        	
//        }
//        
//        if (agtPerformanceShowVO != null || !Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
//        	IFType ifType;
//        	
//        	if (agtDefaultShowVO != null) {
//        		ifType = agtDefaultShowVO.getIfType();
//        	} else if (agtThreadShowVO != null) {
//        		ifType = agtThreadShowVO.getIfType();
//        	} else {
//        		ifType = agtPerformanceShowVO.getIfType();
//        	}
//        	
//        	
//        	if (ifType.equals(IFType.DUBBO) && context.getTestDubboConfigDataModel() != null && !context.getTestDubboConfigDataModel().isSkipTestGen()) {
//        		suit.addRunner(genTestCaseRunner(context.getTestDubboConfigDataModel()));
//	        } else if (ifType.equals(IFType.JSF) && context.getTestJsfConfigDataModel() != null && !context.getTestJsfConfigDataModel().isSkipTestGen()) {
//	        	suit.addRunner(genTestCaseRunner(context.getTestJsfConfigDataModel()));
//	        }
//        } else {
//        	if (context.getTestDubboConfigDataModel() != null && !context.getTestDubboConfigDataModel().isSkipTestGen()) {
//	            suit.addRunner(genTestCaseRunner(context.getTestDubboConfigDataModel()));
//	        }
//        }

        suit.start();
    }

    private static AGTRunner genTestCaseRunner(TestCaseDataModel testCaseDataModel) {
        String className = testCaseDataModel.getGenTestCaseRunner();
        try {
            Constructor<?> constructor = Class.forName(className).getDeclaredConstructor(TestCaseDataModel.class);
            return (AGTRunner)constructor.newInstance(testCaseDataModel);
        } catch (Exception e) {
            throw new AGTBizException("代码生成类错误, 请检查配置", "EXP00011", e);
        }
    }

}
