package lazy.test.plugin.front.validator;

import lazy.test.plugin.front.model.*;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.wizard.WizardPage;

/**
 * Created by wyzhouqiang on 2015/11/25.
 */
public class AGTDetailShowValidator {

    public static void validateModification(WizardPage page, AGTAbstractShowVO vo) {
        page.setErrorMessage(null);
        if (vo.validate()) {
            page.setPageComplete(true);
            return;
        }

        try {
            page.setPageComplete(false);
            String errMsg = (String)MethodUtils.invokeStaticMethod(AGTDetailShowValidator.class, "validate", new Object[]{vo});
            page.setErrorMessage(errMsg);
        } catch (Exception e) {
            throw new AGTBizException("调用校验方法错误", "EXP00010", e);
        }
    }

    public static String validate(AGTDefaultShowVO vo) {
        if (StringUtils.isBlank(vo.getGenPackageName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_PACKAGE;
        } else if(!vo.getGenPackageName().matches("^\\w+[\\.\\w+\\.]+\\w+$") || !vo.getGenResourceName().matches("^\\w+[\\.\\w+\\.]+\\w+$")) {
            return AGTCommonProperties.WIZARD_MSG_ERROR_PACKAGE;
        } else if (StringUtils.isBlank(vo.getGenNormalClassName()) || StringUtils.isBlank(vo.getGenParamClassName()) || StringUtils.isBlank(vo.getGenExceptionClassName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_CLASS;
        } else if (StringUtils.isBlank(vo.getGenNormalMethodName()) || StringUtils.isBlank(vo.getGenParamMethodName()) || StringUtils.isBlank(vo.getGenExceptionMethodName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_METHOD;
        } else if (checkGenClassExist(vo, vo.getGenNormalClassName()) || checkGenClassExist(vo, vo.getGenParamClassName()) || checkGenClassExist(vo, vo.getGenExceptionClassName())) {
            return AGTCommonProperties.WIZARD_MSG_TEST_EXIST;
        } else if (StringUtils.isBlank(vo.getGenNormalTestNum()) || StringUtils.isBlank(vo.getGenParamTestNum()) || StringUtils.isBlank(vo.getGenExceptionTestNum())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_TESTNUM;
        } else if (!StringUtils.isNumeric(vo.getGenNormalTestNum()) || !StringUtils.isNumeric(vo.getGenParamTestNum()) || !StringUtils.isNumeric(vo.getGenExceptionTestNum())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (Integer.valueOf(vo.getGenNormalTestNum()) < 1 || Integer.valueOf(vo.getGenParamTestNum()) < 1 || Integer.valueOf(vo.getGenExceptionTestNum()) < 1) {
            return AGTCommonProperties.WIZARD_MSG_MORE_TESTNUM;
        }
        return AGTCommonProperties.WIZARD_MSG_UNKNOWN;
    }

    public static String validate(AGTThreadShowVO vo) {
        if (StringUtils.isBlank(vo.getGenPackageName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_PACKAGE;
        } else if(!vo.getGenPackageName().matches("^\\w+[\\.\\w+\\.]+\\w+$") || !vo.getGenResourceName().matches("^\\w+[\\.\\w+\\.]+\\w+$")) {
            return AGTCommonProperties.WIZARD_MSG_ERROR_PACKAGE;
        } else if (StringUtils.isBlank(vo.getGenThreadClassName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_CLASS;
        } else if (StringUtils.isBlank(vo.getGenThreadMethodName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_METHOD;
        } else if (checkGenClassExist(vo, vo.getGenThreadClassName())) {
            return AGTCommonProperties.WIZARD_MSG_TEST_EXIST;
        } else if (StringUtils.isBlank(vo.getGenThreadTestNum())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_TESTNUM;
        } else if (!StringUtils.isNumeric(vo.getGenThreadTestNum())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (Integer.valueOf(vo.getGenThreadTestNum()) < 1) {
            return AGTCommonProperties.WIZARD_MSG_MORE_TESTNUM;
        } else if (StringUtils.isBlank(vo.getGenThreadPoolSize())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_POOL_SIZE_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenThreadPoolSize())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (Integer.valueOf(vo.getGenThreadPoolSize()) < Integer.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD))) {
            return AGTCommonProperties.WIZARD_MSG_MORE_THREAD_SIZE + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD);
        } else if (StringUtils.isBlank(vo.getGenTotalInvokeCount())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_INVOKE_COUNT_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenTotalInvokeCount())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (Integer.valueOf(vo.getGenTotalInvokeCount()) < Integer.valueOf(vo.getGenThreadPoolSize())) {
            return AGTCommonProperties.WIZARD_MSG_MORE_THREAD_INVOKE;
        }
        return AGTCommonProperties.WIZARD_MSG_UNKNOWN;
    }

    public static String validate(AGTPerformanceShowVO vo) {
        if (StringUtils.isBlank(vo.getGenPackageName())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_PACKAGE;
        } else if(!vo.getGenPackageName().matches("^\\w+[\\.\\w+\\.]+\\w+$")) {
            return AGTCommonProperties.WIZARD_MSG_ERROR_PACKAGE;
        } else if (StringUtils.isBlank(vo.getGenPerformanceClassName()) || StringUtils.isBlank(vo.getGenPerformanceClassPre()) || StringUtils.isBlank(vo.getGenPerformanceClassPost())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_CLASS;
        } else if (checkGenClassExist(vo, vo.getGenPerformanceClassName()) || checkGenClassExist(vo, vo.getGenPerformanceClassPre()) || checkGenClassExist(vo, vo.getGenPerformanceClassPost())) {
            return AGTCommonProperties.WIZARD_MSG_TEST_EXIST;
        } else if (StringUtils.isBlank(vo.getGenPerformanceTotalThreadNum())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_POOL_SIZE_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenPerformanceTotalThreadNum())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (Integer.valueOf(vo.getGenPerformanceTotalThreadNum()) < Integer.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD))) {
            return AGTCommonProperties.WIZARD_MSG_MORE_THREAD_SIZE + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD);
        } else if (StringUtils.isBlank(vo.getGenPerformanceSingleThreadExecuteTimes())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_INVOKE_COUNT_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenPerformanceSingleThreadExecuteTimes())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (StringUtils.isBlank(vo.getGenPerformanceTotalThreadSetupTime())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_SETUP_TIME_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenPerformanceTotalThreadSetupTime())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        }
        return AGTCommonProperties.WIZARD_MSG_UNKNOWN;
    }

    public static String validate(AGTHttpPerformanceShowVO vo) {
        if (StringUtils.isBlank(vo.getGenHttpUrl())) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_HTTP_URL;
        } else if (!StringUtils.startsWith(vo.getGenHttpUrl(), "http")) {
            return AGTCommonProperties.WIZARD_MSG_BLANK_FORMAT_URL;
        } else if (StringUtils.isBlank(vo.getGenPackageName())) {
            return AGTCommonProperties.WIZARD_MSG_MODIFY_PACKAGE;
        } else if(!vo.getGenPackageName().matches("^\\w+[\\.\\w+\\.]+\\w+$")) {
            return AGTCommonProperties.WIZARD_MSG_ERROR_PACKAGE;
        } else if (StringUtils.isBlank(vo.getGenPerformanceClassName()) || StringUtils.isBlank(vo.getGenPerformanceClassPre()) || StringUtils.isBlank(vo.getGenPerformanceClassPost())) {
            return AGTCommonProperties.WIZARD_MSG_MODIFY_CLASS;
        } else if (checkGenClassExist(vo, vo.getGenPerformanceClassName()) || checkGenClassExist(vo, vo.getGenPerformanceClassPre()) || checkGenClassExist(vo, vo.getGenPerformanceClassPost())) {
            return AGTCommonProperties.WIZARD_MSG_TEST_EXIST;
        } else if (StringUtils.isBlank(vo.getGenPerformanceTotalThreadNum())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_POOL_SIZE_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenPerformanceTotalThreadNum())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (Integer.valueOf(vo.getGenPerformanceTotalThreadNum()) < Integer.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD))) {
            return AGTCommonProperties.WIZARD_MSG_MORE_THREAD_SIZE + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD);
        } else if (StringUtils.isBlank(vo.getGenPerformanceSingleThreadExecuteTimes())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_INVOKE_COUNT_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenPerformanceSingleThreadExecuteTimes())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        } else if (StringUtils.isBlank(vo.getGenPerformanceTotalThreadSetupTime())) {
            return AGTCommonProperties.WIZARD_MSG_THREAD_SETUP_TIME_BLANK;
        } else if (!StringUtils.isNumeric(vo.getGenPerformanceTotalThreadSetupTime())) {
            return AGTCommonProperties.WIZARD_MSG_NUMBERIC_CONFIG;
        }
        return AGTCommonProperties.WIZARD_MSG_UNKNOWN;
    }

    private static boolean checkGenClassExist(AGTAbstractShowVO vo, String genClassName) {
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(vo.getTarProjectName());
        StringBuffer sbf = new StringBuffer();
        sbf.append("/" + AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX);
        sbf.append(vo.getGenPackageName().replaceAll("\\.", "/"));
        sbf.append("/" + genClassName + ".java");

        IFile file = project.getFile(sbf.toString());
        return file.exists();
    }

}
