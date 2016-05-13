package lazy.test.plugin.front.view;

import lazy.test.plugin.front.listener.AGTRadioButtonListener;
import lazy.test.plugin.front.listener.AGTTextModifyListener;
import lazy.test.plugin.front.listener.FocusValidationListener;
import lazy.test.plugin.front.model.AGTThreadShowVO;
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
public class AGTThreadShowPage extends WizardPage {

    private AGTThreadShowVO vo;

    public AGTThreadShowPage(String pageName, String title, AGTApplicationContext context) {
        super(pageName, title, null);
        this.vo = context.getVo();
        this.setPageComplete(false);
    }
    
    public AGTThreadShowVO getVo() {
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

        new Label(testGroup, SWT.NONE).setText(" 资源路径: ");
        Text genResourceNameTX = new Text(testGroup, SWT.BORDER);
        genResourceNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genResourceNameTX.setText(vo.getGenResourceName());
        genResourceNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genResourceName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 测试类名: ");
        Text genThreadClassNameTX = new Text(testGroup, SWT.BORDER);
        genThreadClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genThreadClassNameTX.setText(vo.getGenThreadClassName());
        genThreadClassNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genThreadClassName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 测试方法: ");
        Text genThreadMethodNameTX = new Text(testGroup, SWT.BORDER);
        genThreadMethodNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genThreadMethodNameTX.setText(vo.getGenThreadMethodName());
        genThreadMethodNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genThreadMethodName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 线程数量: ");
        Text genTreadPoolNumTX = new Text(testGroup, SWT.BORDER);
        genTreadPoolNumTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genTreadPoolNumTX.addFocusListener(new FocusValidationListener(this, vo));
        genTreadPoolNumTX.addModifyListener(new AGTTextModifyListener(this, vo, "genThreadPoolSize"));
        genTreadPoolNumTX.setFocus();
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 总运行数: ");
        Text genThreadInvokeCountTX = new Text(testGroup, SWT.BORDER);
        genThreadInvokeCountTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genThreadInvokeCountTX.addModifyListener(new AGTTextModifyListener(this, vo, "genTotalInvokeCount"));
        new Label(testGroup, SWT.NONE);

        this.setControl(composite);
    }

}
