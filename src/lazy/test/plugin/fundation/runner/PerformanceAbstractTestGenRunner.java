package lazy.test.plugin.fundation.runner;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import lazy.test.plugin.front.model.AGTTestClassInfoVO;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.ClassTransferHandler;
import lazy.test.plugin.fundation.handler.FileTransferHandler;
import lazy.test.plugin.fundation.handler.VelocityTransferHandler;
import lazy.test.plugin.fundation.model.ParamMetaDataModel;
import lazy.test.plugin.fundation.model.TestCaseDataModel;

/**
 * Created by wyzhouqiang on 2016/1/4.
 */
public abstract class PerformanceAbstractTestGenRunner extends AGTRunner {

    protected PerformanceAbstractTestGenRunner(TestCaseDataModel model) {
        super(model);
    }

    protected void writeTestCaseSource() {
        Map<String, Object> velocityMap = prepareVelocityMap(model);
        Map<String, Object> velocityPreMap = preparePreVelocityMap(model);
        Map<String, Object> velocityPostMap = preparePostVelocityMap(model);

        String source = VelocityTransferHandler.generateTestCase(model.getGenProjectPath(), model.getGenTestCaseTemplate(), velocityMap);
        String sourcePre = VelocityTransferHandler.generateTestCase(model.getGenProjectPath(), model.getGenTestPreTemplate(), velocityPreMap);
        String sourcePost = VelocityTransferHandler.generateTestCase(model.getGenProjectPath(), model.getGenTestPostTemplate(), velocityPostMap);

        String fileFolder = StringUtils.substringBeforeLast(model.getGenTestCodePath(), "/");
        File sourceFolder = new File(fileFolder);
        if (!sourceFolder.exists()) {
            boolean flag = sourceFolder.mkdirs();
            if (!flag) {
                throw new AGTBizException("测试文件生成失败", "EXP00004");
            }
        }
        FileTransferHandler.copyFileFromFileSystem(new ByteArrayInputStream(source.getBytes()), new File(model.getGenTestCodePath()));
        FileTransferHandler.copyFileFromFileSystem(new ByteArrayInputStream(sourcePre.getBytes()), new File(model.getGenTestPrePath()));
        FileTransferHandler.copyFileFromFileSystem(new ByteArrayInputStream(sourcePost.getBytes()), new File(model.getGenTestPostPath()));
    }

    protected void writeTestCaseData() {
        Map<String, Object> velocityMap = prepareConfigVelocityMap(model);
        String source = VelocityTransferHandler.generateTestCase(model.getGenProjectPath(), model.getGenTestConfigTemplate(), velocityMap);

        String fileFolder = StringUtils.substringBeforeLast(model.getGenTestCodePath(), "/");
        File sourceFolder = new File(fileFolder);
        if (!sourceFolder.exists()) {
            boolean flag = sourceFolder.mkdirs();
            if (!flag) {
                throw new AGTBizException("测试文件生成失败", "EXP00004");
            }
        }
        FileTransferHandler.copyFileFromFileSystem(new ByteArrayInputStream(source.getBytes()), new File(model.getGenTestDataPath()));
    }

    private Map<String, Object> prepareVelocityMap(TestCaseDataModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        AGTTestClassInfoVO classInfo = new AGTTestClassInfoVO();
        classInfo.setGenPackageName(model.getGenPackageName());
        classInfo.setGenImportList(model.getGenImportList());
        classInfo.setGenClassName(model.getGenClassName());

        classInfo.setTestClassName(model.getTestClassName());
        classInfo.setTestClassVar(StringUtils.uncapitalize(model.getTestClassName()));
        classInfo.setTestMethodName(model.getTestMethodName());
        classInfo.setTestMethodParam(StringUtils.strip(model.getTestMethodParamStr()));
        if (model.getTestReturnObject() != null && StringUtils.isNotBlank(model.getTestReturnObject().getReturnStr()) && !StringUtils.equals(model.getTestReturnObject().getReturnStr(), "void")) {
            classInfo.setTestReturnObject(model.getTestReturnObject().getReturnStr());
        }

        StringBuilder buffer = new StringBuilder();
        for (ParamMetaDataModel param : model.getTestParamList()) {
            buffer.append(param.getParamName()).append(", ");
        }
        if (StringUtils.isNotBlank(buffer.toString())) {
            classInfo.setTestInvokeParam(buffer.substring(0, buffer.length() - 2));
        }

        for (ParamMetaDataModel param : model.getTestParamList()) {
            classInfo.getTestParamList().add(ClassTransferHandler.shortGenericClassName(param.getParamStr()) + " " + param.getParamName());
        }

        map.put(AGTCommonProperties.TEST_VELOCITY_CLASS_INFO_KEY, classInfo);
        map.put(AGTCommonProperties.TEST_VELOCITY_HTTP_URL, model.getGenHttpUrl());
        return map;
    }

    private Map<String, Object> preparePreVelocityMap(TestCaseDataModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        AGTTestClassInfoVO classInfo = new AGTTestClassInfoVO();
        classInfo.setGenPackageName(model.getGenPackageName());
        classInfo.setGenClassName(model.getGenClassPre());

        map.put(AGTCommonProperties.TEST_VELOCITY_CLASS_INFO_KEY, classInfo);
        return map;
    }

    private Map<String, Object> preparePostVelocityMap(TestCaseDataModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        AGTTestClassInfoVO classInfo = new AGTTestClassInfoVO();
        classInfo.setGenPackageName(model.getGenPackageName());
        classInfo.setGenClassName(model.getGenClassPost());

        map.put(AGTCommonProperties.TEST_VELOCITY_CLASS_INFO_KEY, classInfo);
        return map;
    }

    private Map<String, Object> prepareConfigVelocityMap(TestCaseDataModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        AGTTestClassInfoVO classInfo = new AGTTestClassInfoVO();
        classInfo.setGenPackageName(model.getGenPackageName());
        classInfo.setGenClassName(model.getGenClassName());
        classInfo.setGenPreName(model.getGenClassPre());
        classInfo.setGenPostName(model.getGenClassPost());

        map.put(AGTCommonProperties.TEST_VELOCITY_CLASS_INFO_KEY, classInfo);
        map.put(AGTCommonProperties.TEST_VELOCITY_JMETER_THREAD_NUM_KEY, model.getGenPerformanceTotalThreadNum());
        map.put(AGTCommonProperties.TEST_VELOCITY_JMETER_THREAD_LOOP_TIMES_KEY, model.getGenPerformanceSingleThreadExecuteTimes());
        map.put(AGTCommonProperties.TEST_VELOCITY_JMETER_THREAD_SETUP_TIME_KEY, model.getGenPerformanceTotalThreadSetupTime());
        return map;
    }
}
