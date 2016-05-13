package lazy.test.plugin.front.view;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lazy.test.plugin.front.listener.AGTRadioButtonListener;
import lazy.test.plugin.front.listener.AGTTextModifyListener;
import lazy.test.plugin.front.listener.ButtonGenTemplateListener;
import lazy.test.plugin.front.listener.ComboDefaultTemplateListener;
import lazy.test.plugin.front.listener.FocusValidationListener;
import lazy.test.plugin.front.model.AGTAbstractShowVO.IFType;
import lazy.test.plugin.front.model.AGTDefaultShowVO;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;
import lazy.test.plugin.fundation.model.AGTApplicationContext;

/**
 * Created by wyzhouqiang on 2015/11/24.
 */
public class AGTDefaultShowPage extends WizardPage {

    private AGTDefaultShowVO vo;

    public AGTDefaultShowPage(String pageName, String title, AGTApplicationContext context) {
        super(pageName, title, null);
        this.vo = context.getVo();
        this.setPageComplete(false);
    }
    
    public AGTDefaultShowVO getVo() {
        return vo;
    }

    @Override
    public void createControl(Composite parent) {

        Composite composite = new Composite(parent, SWT.NONE);

        GridLayout compositeLayout = new GridLayout();
        compositeLayout.numColumns = 5;
        compositeLayout.verticalSpacing = 9;
        composite.setLayout(compositeLayout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        /**目标代码组件*/
        Group targetGroup = new Group(composite, SWT.NONE);
        GridLayout targetLayout = new GridLayout();
        targetLayout.numColumns = 5;
        targetLayout.verticalSpacing = 9;
        targetLayout.marginBottom = 8;
        targetGroup.setLayout(targetLayout);
        targetGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 5, 1));

        new Label(targetGroup, SWT.NONE).setText(" 目标工程: ");
        Text tarProjectNameTX = new Text(targetGroup, SWT.BORDER);
        tarProjectNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1));
        tarProjectNameTX.setText(vo.getTarProjectPath());
        tarProjectNameTX.setEditable(false);

        new Label(targetGroup, SWT.NONE).setText(" 目标类名: ");
        Text tarClassNameTX = new Text(targetGroup, SWT.BORDER);
        tarClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1));
        tarClassNameTX.setText(vo.getTarClassName());
        tarClassNameTX.setEditable(false);

        new Label(targetGroup, SWT.NONE).setText(" 目标方法: ");
        Text tarMethodNameTX = new Text(targetGroup, SWT.BORDER);
        tarMethodNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1));
        tarMethodNameTX.setText(vo.getTarMethodName());
        tarMethodNameTX.setEditable(false);

        /**测试生成组件*/
        Label splitGenLabel = new Label(composite, SWT.CENTER);
        splitGenLabel.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 5, 1));
        splitGenLabel.setText("测试代码配置选项");
        splitGenLabel.setBackground(new Color(null, 183, 217, 237));

        Group testGroup = new Group(composite, SWT.NONE);
        GridLayout testLayout = new GridLayout();
        testLayout.numColumns = 5;
        
        //if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
        	testLayout.verticalSpacing = 9;
        //} else {
        //	testLayout.verticalSpacing = 10;
        //}
        
        testLayout.marginBottom = 8;
        testGroup.setLayout(targetLayout);
        testGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 5, 1));

        /*if (!Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
	        new Label(testGroup, SWT.NONE).setText(" 接口类型: ");
	        Button[] radioButtons = new Button[2];
	    	radioButtons[0] = new Button(testGroup, SWT.RADIO);
	    	radioButtons[0].setText("DUBBO");
	    	radioButtons[0].setSelection(true);
	    	radioButtons[0].setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
	    	
	    	AGTRadioButtonListener dubboButtonListener = new AGTRadioButtonListener(this, radioButtons[0]);
	    	dubboButtonListener.widgetSelected(null);
	    	radioButtons[0].addSelectionListener(dubboButtonListener);
	    	
	    	radioButtons[0].pack();
	    	
	    	radioButtons[1] = new Button(testGroup, SWT.RADIO);
	    	radioButtons[1].setText("JSF");
	    	radioButtons[1].setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));
	    	radioButtons[1].addSelectionListener(new AGTRadioButtonListener(this, radioButtons[1]));
	    	radioButtons[1].pack();
	        new Label(testGroup, SWT.NONE);
        }*/
        vo.setIfType(IFType.DUBBO);
        
        new Label(testGroup, SWT.NONE).setText(" 测试包名: ");
        Text genPackageNameTX = new Text(testGroup, SWT.BORDER);
        genPackageNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genPackageNameTX.setText(vo.getGenPackageName());
        genPackageNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPackageName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 资源路径: ");
        Text genResourceNameTX = new Text(testGroup, SWT.BORDER);
        genResourceNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genResourceNameTX.setText(vo.getGenResourceName());
        genResourceNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genResourceName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 选择模板: ");
        Combo genNormalTemplateCB = new Combo(testGroup, SWT.MouseDoubleClick);
        genNormalTemplateCB.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        String[] normalTemplates = genTemplates(AGTCommonProperties.PROJECT_CONFIG_TEMPLATE_GEN_NORMAL_LIST);
        genNormalTemplateCB.setItems(normalTemplates);
        int indexNormal = findSelectionIndex(normalTemplates, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL);
        genNormalTemplateCB.select(indexNormal);
        genNormalTemplateCB.addSelectionListener(new ComboDefaultTemplateListener(this, vo, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL));
        Combo genExceptionTemplateCB = new Combo(testGroup, SWT.MouseDoubleClick);
        genExceptionTemplateCB.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        String[] exceptionTemplate = genTemplates(AGTCommonProperties.PROJECT_CONFIG_TEMPLATE_GEN_EXCEPTION_LIST);
        genExceptionTemplateCB.setItems(exceptionTemplate);
        int indexException = findSelectionIndex(exceptionTemplate, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION);
        genExceptionTemplateCB.select(indexException);
        genExceptionTemplateCB.addSelectionListener(new ComboDefaultTemplateListener(this, vo, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION));
        Combo genParamTemplateCB = new Combo(testGroup, SWT.MouseDoubleClick);
        genParamTemplateCB.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        String[] paramTemplates = genTemplates(AGTCommonProperties.PROJECT_CONFIG_TEMPLATE_GEN_PARAM_LIST);
        genParamTemplateCB.setItems(paramTemplates);
        int indexParam = findSelectionIndex(paramTemplates, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM);
        genParamTemplateCB.select(indexParam);
        genParamTemplateCB.addSelectionListener(new ComboDefaultTemplateListener(this, vo, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM));
        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_SHOW_NEW_TEMPLATE))) {
            Button button = new Button(testGroup, SWT.NONE);
            button.setText("新建");
            button.addSelectionListener(new ButtonGenTemplateListener(vo));
        } else {
            new Label(testGroup, SWT.NONE);
        }

        new Label(testGroup, SWT.NONE).setText(" 测试类名: ");
        Text genNormalClassNameTX = new Text(testGroup, SWT.BORDER);
        genNormalClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genNormalClassNameTX.setText(vo.getGenNormalClassName());
        genNormalClassNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genNormalClassName"));
        Text genExceptionClassNameTX = new Text(testGroup, SWT.BORDER);
        genExceptionClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genExceptionClassNameTX.setText(vo.getGenExceptionClassName());
        genExceptionClassNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genExceptionClassName"));
        Text genParamClassNameTX = new Text(testGroup, SWT.BORDER);
        genParamClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genParamClassNameTX.setText(vo.getGenParamClassName());
        genParamClassNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genParamClassName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 方 法 名 : ");
        Text genNormalMethodNameTX = new Text(testGroup, SWT.BORDER);
        genNormalMethodNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genNormalMethodNameTX.setText(vo.getGenNormalMethodName());
        genNormalMethodNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genNormalMethodName"));
        Text genExceptionMethodNameTX = new Text(testGroup, SWT.BORDER);
        genExceptionMethodNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genExceptionMethodNameTX.setText(vo.getGenExceptionMethodName());
        genExceptionMethodNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genExceptionMethodName"));
        Text genParamMethodNameTX = new Text(testGroup, SWT.BORDER);
        genParamMethodNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genParamMethodNameTX.setText(vo.getGenParamMethodName());
        genParamMethodNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genParamMethodName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 用例数量: ");
        Text genNormalTestNumTX = new Text(testGroup, SWT.BORDER);
        genNormalTestNumTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genNormalTestNumTX.addModifyListener(new AGTTextModifyListener(this, vo, "genNormalTestNum"));
        genNormalTestNumTX.addFocusListener(new FocusValidationListener(this, vo));
        genNormalTestNumTX.setFocus();
        Text genExceptionTestNumTX = new Text(testGroup, SWT.BORDER);
        genExceptionTestNumTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genExceptionTestNumTX.addModifyListener(new AGTTextModifyListener(this, vo, "genExceptionTestNum"));
        Text genParamTestNumTX = new Text(testGroup, SWT.BORDER);
        genParamTestNumTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genParamTestNumTX.addModifyListener(new AGTTextModifyListener(this, vo, "genParamTestNum"));
        new Label(testGroup, SWT.NONE);

        this.setControl(composite);
    }

    private String[] genTemplates(String configList) {
        List<String> templateList = new ArrayList<String>();
        File[] customerTemplates = vo.getCustomerTemplateList();
        if (customerTemplates != null && customerTemplates.length != 0) {
            for (File file : customerTemplates) {
                templateList.add(file.getName());
            }
        }
        for (String configStr : StringUtils.split(MessageConfigHandler.findMappingResult(configList), ",")) {
            configStr = StringUtils.strip(configStr);
            String configName = MessageConfigHandler.findMappingResult(configStr + "_NAME");
            templateList.add(configName);
        }
        templateList.add(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_TEST_TEMPLATE_SKIP));
        return templateList.toArray(new String[templateList.size()]);
    }

    private int findSelectionIndex(String[] templateArray, String testType) {
        if (StringUtils.equals(testType, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_NORMAL)) {
            if (vo.isSkipGenNormal()) {
                return templateArray.length - 1;
            }
            for (int i = 0; i < templateArray.length; i++) {
                String defaultName = MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_NORMAL) + "_NAME");
                if (StringUtils.equals(templateArray[i], defaultName)) {
                    return i;
                }
            }
        }
        if (StringUtils.equals(testType, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_EXCEPTION)) {
            if (vo.isSkipGenException()) {
                return templateArray.length - 1;
            }
            for (int i = 0; i < templateArray.length; i++) {
                String defaultName = MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_EXCEPTION) + "_NAME");
                if (StringUtils.equals(templateArray[i], defaultName)) {
                    return i;
                }
            }
        }
        if (StringUtils.equals(testType, AGTCommonProperties.PROJECT_TEST_TYPE_DEFAULT_PARAM)) {
            if (vo.isSkipGenParam()) {
                return templateArray.length - 1;
            }
            for (int i = 0; i < templateArray.length; i++) {
                String defaultName = MessageConfigHandler.findMappingResult(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_TYPE_PARAM) + "_NAME");
                if (StringUtils.equals(templateArray[i], defaultName)) {
                    return i;
                }
            }
        }
        return 0;
    }

}
