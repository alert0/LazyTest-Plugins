package lazy.test.plugin.fundation.processor;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazy.test.plugin.front.model.*;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.ClassTransferHandler;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;
import lazy.test.plugin.fundation.model.*;

/**
 * Created by wyzhouqiang on 2015/11/27.
 */
public class TestModelConvertProcessor {

    public static TestCaseDataModel parseNormalTestModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTDefaultShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setGenProjectName(vo.getTarProjectName());
        testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        testCaseDataModel.setGenPackageName(vo.getGenPackageName());
        testCaseDataModel.setGenClassName(vo.getGenNormalClassName());
        testCaseDataModel.setGenMethodName(vo.getGenNormalMethodName());
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setGenTestCaseCode(vo.getGenNormalMethodName());
        testCaseDataModel.setGenTestCaseTemplate(vo.getGenNormalTemplate());
        testCaseDataModel.setGenTestCaseRunner(vo.getGenNormalTestRunner());
        if (!vo.isSkipGenNormal()) {
            testCaseDataModel.setGenTestCaseNum(Integer.valueOf(vo.getGenNormalTestNum()));
        }
        if (vo.isSkipGenNormal() || !StringUtils.endsWithIgnoreCase(vo.getGenNormalTemplate(), ".vm")) {
            testCaseDataModel.setSkipTestGen(true);
        }
        StringBuilder strCode = new StringBuilder();
        strCode.append(vo.getTarProjectPath()).append("/");
        strCode.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strCode.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strCode.append("/" + vo.getGenNormalClassName() + ".java");
        testCaseDataModel.setGenTestCodePath(strCode.toString());

        StringBuilder strData = new StringBuilder();
        strData.append(vo.getTarProjectPath()).append("/");
        strData.append(AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX);
        strData.append(vo.getGenResourceName().replaceAll("\\.", "/"));
        strData.append("/" + vo.getGenNormalClassName());
        strData.append("/" + vo.getGenNormalClassName() + ".csv");
        testCaseDataModel.setGenTestDataPath(strData.toString());

        testCaseDataModel.setGenImportList(genTestCaseImportList(model));
        testCaseDataModel.setTestParamList(model.getMethodMetaDataModel().getParamList());
        testCaseDataModel.setTestReturnObject(model.getMethodMetaDataModel().getReturnObject());

        return testCaseDataModel;
    }

    public static TestCaseDataModel parseExceptionTestModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTDefaultShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setGenProjectName(vo.getTarProjectName());
        testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        testCaseDataModel.setGenPackageName(vo.getGenPackageName());
        testCaseDataModel.setGenClassName(vo.getGenExceptionClassName());
        testCaseDataModel.setGenMethodName(vo.getGenExceptionMethodName());
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setGenTestCaseCode(vo.getGenExceptionMethodName());
        testCaseDataModel.setGenTestCaseTemplate(vo.getGenExceptionTemplate());
        testCaseDataModel.setGenTestCaseRunner(vo.getGenExceptionTestRunner());
        if (!vo.isSkipGenException()) {
            testCaseDataModel.setGenTestCaseNum(Integer.valueOf(vo.getGenExceptionTestNum()));
        }
        if (vo.isSkipGenException() ||!StringUtils.endsWithIgnoreCase(vo.getGenExceptionTemplate(), ".vm")) {
            testCaseDataModel.setSkipTestGen(true);
        }
        StringBuilder strCode = new StringBuilder();
        strCode.append(vo.getTarProjectPath()).append("/");
        strCode.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strCode.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strCode.append("/" + vo.getGenExceptionClassName() + ".java");
        testCaseDataModel.setGenTestCodePath(strCode.toString());

        StringBuilder strData = new StringBuilder();
        strData.append(vo.getTarProjectPath()).append("/");
        strData.append(AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX);
        strData.append(vo.getGenResourceName().replaceAll("\\.", "/"));
        strData.append("/" + vo.getGenExceptionClassName());
        strData.append("/" + vo.getGenExceptionClassName() + ".csv");
        testCaseDataModel.setGenTestDataPath(strData.toString());

        testCaseDataModel.setGenImportList(genTestCaseImportList(model));
        testCaseDataModel.setTestParamList(model.getMethodMetaDataModel().getParamList());
        testCaseDataModel.setTestReturnObject(model.getMethodMetaDataModel().getReturnObject());

        return testCaseDataModel;
    }

    public static TestCaseDataModel parseParamTestModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTDefaultShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setGenProjectName(vo.getTarProjectName());
        testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        testCaseDataModel.setGenPackageName(vo.getGenPackageName());
        testCaseDataModel.setGenClassName(vo.getGenParamClassName());
        testCaseDataModel.setGenMethodName(vo.getGenParamMethodName());
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setGenTestCaseCode(vo.getGenParamMethodName());
        testCaseDataModel.setGenTestCaseTemplate(vo.getGenParamTemplate());
        testCaseDataModel.setGenTestCaseRunner(vo.getGenParamTestRunner());
        if (!vo.isSkipGenParam()) {
            testCaseDataModel.setGenTestCaseNum(Integer.valueOf(vo.getGenParamTestNum()));
        }
        if (vo.isSkipGenParam() || !StringUtils.endsWithIgnoreCase(vo.getGenParamTemplate(), ".vm")) {
            testCaseDataModel.setSkipTestGen(true);
        }
        StringBuilder strCode = new StringBuilder();
        strCode.append(vo.getTarProjectPath()).append("/");
        strCode.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strCode.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strCode.append("/" + vo.getGenParamClassName() + ".java");
        testCaseDataModel.setGenTestCodePath(strCode.toString());

        StringBuilder strData = new StringBuilder();
        strData.append(vo.getTarProjectPath()).append("/");
        strData.append(AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX);
        strData.append(vo.getGenResourceName().replaceAll("\\.", "/"));
        strData.append("/" + vo.getGenParamClassName());
        strData.append("/" + vo.getGenParamClassName() + ".csv");
        testCaseDataModel.setGenTestDataPath(strData.toString());

        testCaseDataModel.setGenImportList(genTestCaseImportList(model));
        testCaseDataModel.setTestParamList(model.getMethodMetaDataModel().getParamList());
        testCaseDataModel.setTestReturnObject(model.getMethodMetaDataModel().getReturnObject());

        return testCaseDataModel;
    }

    public static TestCaseDataModel parseThreadTestModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTThreadShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setGenProjectName(vo.getTarProjectName());
        testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        testCaseDataModel.setGenPackageName(vo.getGenPackageName());
        testCaseDataModel.setGenClassName(vo.getGenThreadClassName());
        testCaseDataModel.setGenMethodName(vo.getGenThreadMethodName());
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setGenTestCaseCode(vo.getGenThreadMethodName());
        testCaseDataModel.setGenTestCaseTemplate(vo.getGenThreadTemplate());
        testCaseDataModel.setGenTestCaseRunner(vo.getGenThreadTestRunner());
        testCaseDataModel.setGenTestCaseNum(Integer.valueOf(vo.getGenThreadTestNum()));
        testCaseDataModel.setGenTestThreadPoolSize(Integer.valueOf(vo.getGenThreadPoolSize()));
        testCaseDataModel.setGenTotalInvocationCount(Integer.valueOf(vo.getGenTotalInvokeCount()));
        StringBuilder strCode = new StringBuilder();
        strCode.append(vo.getTarProjectPath()).append("/");
        strCode.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strCode.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strCode.append("/" + vo.getGenThreadClassName() + ".java");
        testCaseDataModel.setGenTestCodePath(strCode.toString());

        StringBuilder strData = new StringBuilder();
        strData.append(vo.getTarProjectPath()).append("/");
        strData.append(AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX);
        strData.append(vo.getGenResourceName().replaceAll("\\.", "/"));
        strData.append("/" + vo.getGenThreadClassName());
        strData.append("/" + vo.getGenThreadClassName() + ".csv");
        testCaseDataModel.setGenTestDataPath(strData.toString());

        testCaseDataModel.setGenImportList(genTestCaseImportList(model));
        testCaseDataModel.setTestParamList(model.getMethodMetaDataModel().getParamList());
        testCaseDataModel.setTestReturnObject(model.getMethodMetaDataModel().getReturnObject());

        return testCaseDataModel;
    }

    public static TestCaseDataModel parsePerformanceTestModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTPerformanceShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setGenProjectName(vo.getTarProjectName());
        testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        testCaseDataModel.setGenPackageName(vo.getGenPackageName());
        testCaseDataModel.setGenClassPre(vo.getGenPerformanceClassPre());
        testCaseDataModel.setGenClassName(vo.getGenPerformanceClassName());
        testCaseDataModel.setGenClassPost(vo.getGenPerformanceClassPost());
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setGenTestPreTemplate(vo.getGenPerformancePreTemplate());
        testCaseDataModel.setGenTestCaseTemplate(vo.getGenPerformanceClassTemplate());
        testCaseDataModel.setGenTestPostTemplate(vo.getGenPerformancePostTemplate());
        testCaseDataModel.setGenTestConfigTemplate(vo.getGenPerformanceConfigTemplate());
        testCaseDataModel.setGenTestCaseRunner(vo.getGenPerformanceTestRunner());
        testCaseDataModel.setGenPerformanceSingleThreadExecuteTimes(Integer.valueOf(vo.getGenPerformanceSingleThreadExecuteTimes()));
        testCaseDataModel.setGenPerformanceTotalThreadNum(Integer.valueOf(vo.getGenPerformanceTotalThreadNum()));
        testCaseDataModel.setGenPerformanceTotalThreadSetupTime(Integer.valueOf(vo.getGenPerformanceTotalThreadSetupTime()));

        StringBuilder strCode = new StringBuilder();
        strCode.append(vo.getTarProjectPath()).append("/");
        strCode.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strCode.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strCode.append("/" + vo.getGenPerformanceClassName() + ".java");
        testCaseDataModel.setGenTestCodePath(strCode.toString());

        StringBuilder strPre = new StringBuilder();
        strPre.append(vo.getTarProjectPath()).append("/");
        strPre.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strPre.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strPre.append("/" + vo.getGenPerformanceClassPre() + ".java");
        testCaseDataModel.setGenTestPrePath(strPre.toString());

        StringBuilder strPost = new StringBuilder();
        strPost.append(vo.getTarProjectPath()).append("/");
        strPost.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strPost.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strPost.append("/" + vo.getGenPerformanceClassPost() + ".java");
        testCaseDataModel.setGenTestPostPath(strPost.toString());

        StringBuilder strData = new StringBuilder();
        strData.append(vo.getTarProjectPath()).append("/");
        strData.append(AGTCommonProperties.PROJECT_TEST_ROOT_PATH_PREFIX);
        strData.append("jmeter/");
        strData.append(model.getClassSimpleName() + "_" + vo.getGenPerformanceClassName() + ".jmx");
        testCaseDataModel.setGenTestDataPath(strData.toString());

        testCaseDataModel.setGenImportList(genTestCaseImportList(model));
        testCaseDataModel.setTestParamList(model.getMethodMetaDataModel().getParamList());
        testCaseDataModel.setTestReturnObject(model.getMethodMetaDataModel().getReturnObject());

        return testCaseDataModel;
    }

    public static TestCaseDataModel parseHttpPerformanceTestModel(AGTApplicationContext context) {
        AGTHttpPerformanceShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setGenProjectName(vo.getTarProjectName());
        testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        testCaseDataModel.setGenPackageName(vo.getGenPackageName());
        testCaseDataModel.setGenHttpUrl(vo.getGenHttpUrl());
        testCaseDataModel.setGenClassPre(vo.getGenPerformanceClassPre());
        testCaseDataModel.setGenClassName(vo.getGenPerformanceClassName());
        testCaseDataModel.setGenClassPost(vo.getGenPerformanceClassPost());
        testCaseDataModel.setGenTestPreTemplate(vo.getGenPerformancePreTemplate());
        testCaseDataModel.setGenTestCaseTemplate(vo.getGenPerformanceClassTemplate());
        testCaseDataModel.setGenTestPostTemplate(vo.getGenPerformancePostTemplate());
        testCaseDataModel.setGenTestConfigTemplate(vo.getGenPerformanceConfigTemplate());
        testCaseDataModel.setGenTestCaseRunner(vo.getGenPerformanceTestRunner());
        testCaseDataModel.setGenPerformanceSingleThreadExecuteTimes(Integer.valueOf(vo.getGenPerformanceSingleThreadExecuteTimes()));
        testCaseDataModel.setGenPerformanceTotalThreadNum(Integer.valueOf(vo.getGenPerformanceTotalThreadNum()));
        testCaseDataModel.setGenPerformanceTotalThreadSetupTime(Integer.valueOf(vo.getGenPerformanceTotalThreadSetupTime()));

        StringBuilder strCode = new StringBuilder();
        strCode.append(vo.getTarProjectPath()).append("/");
        strCode.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strCode.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strCode.append("/" + vo.getGenPerformanceClassName() + ".java");
        testCaseDataModel.setGenTestCodePath(strCode.toString());

        StringBuilder strPre = new StringBuilder();
        strPre.append(vo.getTarProjectPath()).append("/");
        strPre.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strPre.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strPre.append("/" + vo.getGenPerformanceClassPre() + ".java");
        testCaseDataModel.setGenTestPrePath(strPre.toString());

        StringBuilder strPost = new StringBuilder();
        strPost.append(vo.getTarProjectPath()).append("/");
        strPost.append(AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        strPost.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        strPost.append("/" + vo.getGenPerformanceClassPost() + ".java");
        testCaseDataModel.setGenTestPostPath(strPost.toString());

        StringBuilder strData = new StringBuilder();
        strData.append(vo.getTarProjectPath()).append("/");
        strData.append(AGTCommonProperties.PROJECT_TEST_ROOT_PATH_PREFIX);
        strData.append("jmeter/");
        strData.append("HTTP_" + vo.getGenPerformanceClassName() + ".jmx");
        testCaseDataModel.setGenTestDataPath(strData.toString());

        return testCaseDataModel;
    }

    public static TestCaseDataModel parseDubboConfigModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTAbstractShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setSkipTestGen(!Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_IFREG)));
        testCaseDataModel.setGenTestCaseRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DUBBO_CONFIG_RUNNER));

        if (vo != null && StringUtils.isNotBlank(vo.getTarProjectPath())) {
            testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        }

        return testCaseDataModel;
    }

    public static TestCaseDataModel parseJsfConfigModel(AGTApplicationContext context) {
        ClassMetaDataModel model = context.getClassMetaDataModel();
        AGTAbstractShowVO vo = context.getVo();
        TestCaseDataModel testCaseDataModel = new TestCaseDataModel();
        testCaseDataModel.setTestClassName(model.getClassSimpleName());
        testCaseDataModel.setTestFullClassName(model.getClassQualifiedName());
        testCaseDataModel.setTestMethodName(model.getMethodMetaDataModel().getMethodSimpleName());
        testCaseDataModel.setTestMethodParamStr(StringUtils.substringBetween(model.getMethodMetaDataModel().getMethodQualifiedName(), "(", ")"));
        testCaseDataModel.setSkipTestGen(!Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_IFREG)));
        testCaseDataModel.setGenTestCaseRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_JSF_CONFIG_RUNNER));

        if (vo != null && StringUtils.isNotBlank(vo.getTarProjectPath())) {
            testCaseDataModel.setGenProjectPath(vo.getTarProjectPath());
        }

        return testCaseDataModel;
    }

    private static List<String> genTestCaseImportList(ClassMetaDataModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new ArrayList<String>();
        ReturnMetaDataModel returnObject = model.getMethodMetaDataModel().getReturnObject();
        map.putAll(returnObject.getReturnMap());
        list.addAll(ClassTransferHandler.fetchAllClassType(map));
        map.clear();
        List<ParamMetaDataModel> paramList = model.getMethodMetaDataModel().getParamList();
        for (ParamMetaDataModel param : paramList) {
            map.putAll(param.getParamMap());
            list.addAll(ClassTransferHandler.fetchAllClassType(map));
            map.clear();
        }
        list.add(model.getClassQualifiedName());

        List<String> result = new ArrayList<String>();
        for (String str : list) {
            if (result.contains(str) || !StringUtils.contains(str, ".") || StringUtils.contains(str, "java.lang")) {
                continue;
            }
            result.add(str);
        }

        return result;
    }
}
