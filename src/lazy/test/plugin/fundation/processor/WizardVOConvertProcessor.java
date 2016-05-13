package lazy.test.plugin.fundation.processor;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jface.viewers.IStructuredSelection;

import java.io.File;

import lazy.test.plugin.front.model.AGTDefaultShowVO;
import lazy.test.plugin.front.model.AGTHttpPerformanceShowVO;
import lazy.test.plugin.front.model.AGTPerformanceShowVO;
import lazy.test.plugin.front.model.AGTThreadShowVO;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.model.ClassMetaDataModel;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class WizardVOConvertProcessor {

    public static AGTDefaultShowVO parseAGTDefaultShowVO(AGTApplicationContext context, IStructuredSelection selection) {
        IMember member = (IMember)selection.getFirstElement();
        IJavaProject project = member.getJavaProject();
        ClassMetaDataModel model = context.getClassMetaDataModel();

        AGTDefaultShowVO vo = new AGTDefaultShowVO();
        vo.setTarProjectName(project.getProject().getName());
        vo.setTarProjectPath(new File(project.getProject().getLocationURI()).getAbsolutePath());
        vo.setTarClassName(model.getClassQualifiedName());
        vo.setTarMethodName(model.getMethodMetaDataModel().getMethodQualifiedName());
        
        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
        	vo.setGenPackageName(model.getClassQualifiedName().replace(model.getClassSimpleName(), StringUtils.uncapitalize(model.getClassSimpleName())) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
            vo.setGenResourceName(model.getClassQualifiedName().replace(model.getClassSimpleName(), StringUtils.uncapitalize(model.getClassSimpleName())) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
        } else {
	        vo.setGenPackageName("test.commonTest." + StringUtils.uncapitalize(model.getClassSimpleName()) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
	        vo.setGenResourceName("data.commonTest." + StringUtils.uncapitalize(model.getClassSimpleName()) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
        }
        
        vo.setGenNormalClassName(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "NormalTest");
        vo.setGenNormalMethodName("test" + StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "Normal");
        vo.setGenNormalTestRunner(MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_NORMAL) + "_CLASS"));
        vo.setGenNormalTemplate(prepareNormalTemplatePath(vo.getTarProjectPath()));
        vo.setSkipGenNormal(Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_SKIP_NORMAL)));
        vo.setGenExceptionClassName(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "ExceptionTest");
        vo.setGenExceptionMethodName("test" + StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "Exception");
        vo.setGenExceptionTestRunner(MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_EXCEPTION) + "_CLASS"));
        vo.setGenExceptionTemplate(prepareExceptionTemplatePath(vo.getTarProjectPath()));
        vo.setSkipGenException(Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_SKIP_EXCEPTION)));
        vo.setGenParamClassName(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "ParamTest");
        vo.setGenParamMethodName("test" + StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "Param");
        vo.setGenParamTestRunner(MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PARAM) + "_CLASS"));
        vo.setGenParamTemplate(prepareParamTemplatePath(vo.getTarProjectPath()));
        vo.setSkipGenParam(Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_SKIP_PARAM)));
        vo.setCustomerTemplateList(readCustomerTemplate(vo.getTarProjectPath()));
        return vo;
    }

    public static AGTThreadShowVO parseAGTThreadShowVO(AGTApplicationContext context, IStructuredSelection selection) {
        IMember member = (IMember)selection.getFirstElement();
        IJavaProject project = member.getJavaProject();
        ClassMetaDataModel model = context.getClassMetaDataModel();

        AGTThreadShowVO vo = new AGTThreadShowVO();
        vo.setTarProjectName(project.getProject().getName());
        vo.setTarProjectPath(new File(project.getProject().getLocationURI()).getAbsolutePath());
        vo.setTarClassName(model.getClassQualifiedName());
        vo.setTarMethodName(model.getMethodMetaDataModel().getMethodQualifiedName());
        
        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
        	vo.setGenPackageName(model.getClassQualifiedName().replace(model.getClassSimpleName(), StringUtils.uncapitalize(model.getClassSimpleName())) + "." + model.getMethodMetaDataModel().getMethodSimpleName() + ".concurrencyTest");
            vo.setGenResourceName(model.getClassQualifiedName().replace(model.getClassSimpleName(), StringUtils.uncapitalize(model.getClassSimpleName())) + "." + model.getMethodMetaDataModel().getMethodSimpleName() + ".concurrencyTest");
        } else {
        	vo.setGenPackageName("test.concurrencyTest." + StringUtils.uncapitalize(model.getClassSimpleName()) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
            vo.setGenResourceName("data.concurrencyTest." + StringUtils.uncapitalize(model.getClassSimpleName()) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
        }
        
        vo.setGenThreadClassName(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "ConcurrentTest");
        vo.setGenThreadMethodName("test" + StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "Concurrent");
        vo.setGenThreadTestNum("1");
        vo.setGenThreadTestRunner(MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_CONCURRENT) + "_CLASS"));
        vo.setGenThreadTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_CONCURRENT) + "_FILE"));
        return vo;
    }

    public static AGTPerformanceShowVO parseAGTPerformanceShowVO(AGTApplicationContext context, IStructuredSelection selection) {
        IMember member = (IMember)selection.getFirstElement();
        IJavaProject project = member.getJavaProject();
        ClassMetaDataModel model = context.getClassMetaDataModel();

        AGTPerformanceShowVO vo = new AGTPerformanceShowVO();
        vo.setTarProjectName(project.getProject().getName());
        vo.setTarProjectPath(new File(project.getProject().getLocationURI()).getAbsolutePath());
        vo.setTarClassName(model.getClassQualifiedName());
        vo.setTarMethodName(model.getMethodMetaDataModel().getMethodQualifiedName());
        vo.setGenPackageName("test.performanceTest." + StringUtils.uncapitalize(model.getClassSimpleName()) + "." + model.getMethodMetaDataModel().getMethodSimpleName());
        vo.setGenPerformanceClassTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE) + "_FILE"));
        vo.setGenPerformancePreTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE) + "_FILE_PREPOST"));
        vo.setGenPerformancePostTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE) + "_FILE_PREPOST"));
        vo.setGenPerformanceConfigTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE) + "_FILE_CONFIG"));
        vo.setGenPerformanceClassName(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "PerformanceTest");
        vo.setGenPerformanceClassPre(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "PerformancePre");
        vo.setGenPerformanceClassPost(StringUtils.capitalize(model.getMethodMetaDataModel().getMethodSimpleName()) + "PerformancePost");
        vo.setGenPerformanceTestRunner(MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE) + "_CLASS"));
        return vo;
    }

    public static AGTHttpPerformanceShowVO parseAGTHttpPerformanceShowVO(IStructuredSelection selection) {
        IPackageFragment packageFragment = (IPackageFragment)selection.getFirstElement();
        IJavaProject project = packageFragment.getJavaProject();

        AGTHttpPerformanceShowVO vo = new AGTHttpPerformanceShowVO();
        vo.setTarProjectName(project.getProject().getName());
        vo.setTarProjectPath(new File(project.getProject().getLocationURI()).getAbsolutePath());
        vo.setGenPerformanceClassTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE_HTTP) + "_FILE"));
        vo.setGenPerformancePreTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE_HTTP) + "_FILE_PREPOST"));
        vo.setGenPerformancePostTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE_HTTP) + "_FILE_PREPOST"));
        vo.setGenPerformanceConfigTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE_HTTP) + "_FILE_CONFIG"));
        vo.setGenPerformanceTestRunner(MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE_HTTP) + "_CLASS"));
        return vo;
    }

    private static String prepareNormalTemplatePath(String custProjectPath) {
        String templateName = MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_NORMAL) + "_FILE");
        File[] fileArray = readCustomerTemplate(custProjectPath);
        if (fileArray != null && fileArray.length != 0) {
            for (File file : fileArray) {
                if (StringUtils.equals(templateName, file.getName())) {
                    return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + file.getName();
                }
            }
        }

        if (StringUtils.isNotBlank(templateName)) {
            return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + templateName;
        }
        return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL + "_FILE");
    }

    private static String prepareExceptionTemplatePath(String custProjectPath) {
        String templateName = MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_EXCEPTION) + "_FILE");
        File[] fileArray = readCustomerTemplate(custProjectPath);
        if (fileArray != null && fileArray.length != 0) {
            for (File file : fileArray) {
                if (StringUtils.equals(templateName, file.getName())) {
                    return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + file.getName();
                }
            }
        }
        if (StringUtils.isNotBlank(templateName)) {
            return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + templateName;
        }
        return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION + "_FILE");
    }

    private static String prepareParamTemplatePath(String custProjectPath) {
        String templateName = MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PARAM) + "_FILE");
        File[] fileArray = readCustomerTemplate(custProjectPath);
        if (fileArray != null && fileArray.length != 0) {
            for (File file : fileArray) {
                if (StringUtils.equals(templateName, file.getName())) {
                    return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + file.getName();
                }
            }
        }
        if (StringUtils.isNotBlank(templateName)) {
            return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + templateName;
        }
        return AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM + "_FILE");
    }

    private static File[] readCustomerTemplate(String custProjectPath) {
        File projectDir = new File(custProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX);
        if (projectDir.exists()) {
            File[] fileList = projectDir.listFiles();
            if (fileList != null && fileList.length != 0) {
                return fileList;
            }
        }
        return null;
    }

}
