package lazy.test.plugin.front.listener;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

import java.io.File;

import lazy.test.plugin.front.model.AGTDefaultShowVO;
import lazy.test.plugin.front.validator.AGTDetailShowValidator;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;

/**
 * Created by wyzhouqiang on 2015/12/8.
 */
public class ComboDefaultTemplateListener implements SelectionListener {

    private WizardPage page;

    private AGTDefaultShowVO vo;

    private String testType;

    public ComboDefaultTemplateListener(WizardPage page, AGTDefaultShowVO vo, String testType) {
        this.page = page;
        this.vo = vo;
        this.testType = testType;
    }

    @Override
    public void widgetSelected(SelectionEvent selectionEvent) {
        Object evenObj = selectionEvent.getSource();
        if (evenObj instanceof Combo) {
            selectionNormalTemplate((Combo)evenObj);
        }

        AGTDetailShowValidator.validateModification(page, vo);
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent selectionEvent) {
        Object evenObj = selectionEvent.getSource();
        if (evenObj instanceof Combo) {
            selectionNormalTemplate((Combo)evenObj);
        }
        
        AGTDetailShowValidator.validateModification(page, vo);
    }

    private void selectionNormalTemplate(Combo combo) {
        String text = combo.getText();

        if (StringUtils.equals(testType, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL)) {
            if (StringUtils.equals(text, MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TEMPLATE_SKIP))) {
                vo.setSkipGenNormal(true);
                return;
            }
            vo.setSkipGenNormal(false);
            String customerTemplate = findCustomerTemplateMatch(text);
            if (StringUtils.isNotBlank(customerTemplate)) {
                vo.setGenNormalTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + customerTemplate);
                vo.setGenNormalTestRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL + "_CLASS"));
                return;
            }
            String rawList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_TEMPLATE_GEN_NORMAL_LIST);
            for (String configStr : StringUtils.split(rawList, ",")) {
                configStr = StringUtils.strip(configStr);
                String configName = MessageConfigHandler.findMappingResult(configStr + "_NAME");
                if (StringUtils.isNotBlank(configName) && StringUtils.equals(configName, text)) {
                    vo.setGenNormalTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(configStr + "_FILE"));
                    vo.setGenNormalTestRunner(MessageConfigHandler.findMappingResult(configStr + "_CLASS"));
                    return;
                }
            }
            vo.setGenNormalTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL + "_FILE"));
            vo.setGenNormalTestRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL + "_CLASS"));
        }
        if (StringUtils.equals(testType, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION)) {
            if (StringUtils.equals(text, MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TEMPLATE_SKIP))) {
                vo.setSkipGenException(true);
                return;
            }
            vo.setSkipGenException(false);
            String customerTemplate = findCustomerTemplateMatch(text);
            if (StringUtils.isNotBlank(customerTemplate)) {
                vo.setGenExceptionTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + customerTemplate);
                vo.setGenExceptionTestRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION + "_CLASS"));
                return;
            }
            String rawList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_TEMPLATE_GEN_EXCEPTION_LIST);
            for (String configStr : StringUtils.split(rawList, ",")) {
                configStr = StringUtils.strip(configStr);
                String configName = MessageConfigHandler.findMappingResult(configStr + "_NAME");
                if (StringUtils.isNotBlank(configName) && StringUtils.equals(configName, text)) {
                    vo.setGenExceptionTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(configStr + "_FILE"));
                    vo.setGenExceptionTestRunner(MessageConfigHandler.findMappingResult(configStr + "_CLASS"));
                    return;
                }
            }
            vo.setGenExceptionTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION + "_FILE"));
            vo.setGenExceptionTestRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION + "_CLASS"));
        }
        if (StringUtils.equals(testType, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM)) {
            if (StringUtils.equals(text, MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TEMPLATE_SKIP))) {
                vo.setSkipGenParam(true);
                return;
            }
            vo.setSkipGenParam(false);
            String customerTemplate = findCustomerTemplateMatch(text);
            if (StringUtils.isNotBlank(customerTemplate)) {
                vo.setGenParamTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + customerTemplate);
                vo.setGenParamTestRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM + "_CLASS"));
                return;
            }
            String rawList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_TEMPLATE_GEN_PARAM_LIST);
            for (String configStr : StringUtils.split(rawList, ",")) {
                configStr = StringUtils.strip(configStr);
                String configName = MessageConfigHandler.findMappingResult(configStr + "_NAME");
                if (StringUtils.isNotBlank(configName) && StringUtils.equals(configName, text)) {
                    vo.setGenParamTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(configStr + "_FILE"));
                    vo.setGenParamTestRunner(MessageConfigHandler.findMappingResult(configStr + "_CLASS"));
                    return;
                }
            }
            vo.setGenParamTemplate(AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM + "_FILE"));
            vo.setGenParamTestRunner(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM + "_CLASS"));
        }
    }

    private String findCustomerTemplateMatch(String text) {
        File [] fileArray = vo.getCustomerTemplateList();
        if (fileArray == null || fileArray.length == 0) {
            return null;
        }
        for (File file : fileArray) {
            if (file != null && StringUtils.equals(file.getName(), text)) {
                return text;
            }
        }
        return null;
    }
}
