package lazy.test.plugin.front.view;

import lazy.test.plugin.front.listener.AGTTextModifyListener;
import lazy.test.plugin.front.listener.FocusValidationListener;
import lazy.test.plugin.front.model.AGTHttpPerformanceShowVO;
import lazy.test.plugin.fundation.model.AGTApplicationContext;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Created by wyzhouqiang on 2015/11/24.
 */
public class AGTHttpPerformanceShowPage extends WizardPage {

    private AGTHttpPerformanceShowVO vo;

    public AGTHttpPerformanceShowPage(String pageName, String title, AGTApplicationContext context) {
        super(pageName, title, null);
        this.vo = context.getVo();
        this.setPageComplete(false);
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

        new Label(targetGroup, SWT.NONE).setText(" 测试地址: ");
        Text genUrlNameTX = new Text(targetGroup, SWT.BORDER);
        genUrlNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 4, 1));
        genUrlNameTX.addFocusListener(new FocusValidationListener(this, vo));
        genUrlNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genHttpUrl"));
        genUrlNameTX.setFocus();

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

        new Label(testGroup, SWT.NONE).setText(" 测试包名: ");
        Text genPackageNameTX = new Text(testGroup, SWT.BORDER);
        genPackageNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genPackageNameTX.setText("test.performanceTest.http");
        genPackageNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPackageName"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 测试类名: ");
        Text genPerformanceClassPreTX = new Text(testGroup, SWT.BORDER);
        genPerformanceClassPreTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genPerformanceClassPreTX.setText("ClassNamePre");
        genPerformanceClassPreTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceClassPre"));

        Text genPerformanceClassNameTX = new Text(testGroup, SWT.BORDER);
        genPerformanceClassNameTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genPerformanceClassNameTX.setText("ClassNameTest");
        genPerformanceClassNameTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceClassName"));

        Text genPerformanceClassPostTX = new Text(testGroup, SWT.BORDER);
        genPerformanceClassPostTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1));
        genPerformanceClassPostTX.setText("ClassNamePost");
        genPerformanceClassPostTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceClassPost"));
        new Label(testGroup, SWT.NONE);

        new Label(testGroup, SWT.NONE).setText(" 线程数量: ");
        Text genTotalThreadNumTX = new Text(testGroup, SWT.BORDER);
        genTotalThreadNumTX.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 3, 1));
        genTotalThreadNumTX.addModifyListener(new AGTTextModifyListener(this, vo, "genPerformanceTotalThreadNum"));
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
