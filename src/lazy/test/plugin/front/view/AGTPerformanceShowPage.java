package lazy.test.plugin.front.view;

import lazy.test.plugin.front.listener.AGTRadioButtonListener;
import lazy.test.plugin.front.listener.AGTTextModifyListener;
import lazy.test.plugin.front.listener.FocusValidationListener;
import lazy.test.plugin.front.model.AGTPerformanceShowVO;
import lazy.test.plugin.front.model.AGTAbstractShowVO.IFType;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;
import lazy.test.plugin.fundation.model.AGTApplicationContext;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Created by wyzhouqiang on 2015/11/24.
 */
public class AGTPerformanceShowPage extends WizardPage {

    private AGTPerformanceShowVO vo;

    public AGTPerformanceShowPage(String pageName, String title, AGTApplicationContext context) {
        super(pageName, title, null);
        this.vo = context.getVo();
        this.setPageComplete(false);
    }
    
    public AGTPerformanceShowVO getVo() {
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
        testLayout.verticalSpacing = 9;
        testLayout.marginBottom = 8;
        testGroup.setLayout(targetLayout);
        testGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 5, 1));

        /*if (!Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
	        new Label(testGroup, SWT.NONE).setText(" 接口类型: ");
	        Button[] radioButtons = new Button[2];
	    	radioButtons[0] = new Button(testGroup, SWT.RADIO);
	    	radioButtons[0].setSelection(true);
	    	radioButtons[0].setText("DUBBO");
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

        new Label(testGroup, SWT.NONE).setText(" 测试类名: ");
        Text genPerformanceClassPreTX = new Text(testGroup, SWT.BORDER);
        genPerformanceClassPreTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genPerformanceClassPreTX.setText(vo.getGenPerformanceClassPre());
        genPerformanceClassPreTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceClassPre"));

        Text genPerformanceClassNameTX = new Text(testGroup, SWT.BORDER);
        genPerformanceClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genPerformanceClassNameTX.setText(vo.getGenPerformanceClassName());
        genPerformanceClassNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceClassName"));

        Text genPerformanceClassPostTX = new Text(testGroup, SWT.BORDER);
        genPerformanceClassPostTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genPerformanceClassPostTX.setText(vo.getGenPerformanceClassPost());
        genPerformanceClassPostTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceClassPost"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 线程数量: ");
        Text genTotalThreadNumTX = new Text(testGroup, SWT.BORDER);
        genTotalThreadNumTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genTotalThreadNumTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceTotalThreadNum"));
        genTotalThreadNumTX.addFocusListener(new FocusValidationListener(this, vo));
        genTotalThreadNumTX.setFocus();
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 线程执行次数: ");
        Text genSingleThreadExecuteTimesTX = new Text(testGroup, SWT.BORDER);
        genSingleThreadExecuteTimesTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genSingleThreadExecuteTimesTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceSingleThreadExecuteTimes"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 线程启动时间: ");
        Text genTotalThreadSetupTime = new Text(testGroup, SWT.BORDER);
        genTotalThreadSetupTime.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genTotalThreadSetupTime.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceTotalThreadSetupTime"));
        new Label(testGroup, SWT.NONE);

        this.setControl(composite);
    }

}
