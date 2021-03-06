package lazy.test.plugin.front.view;

import lazy.test.plugin.front.model.AGTPerformanceShowVO;
import lazy.test.plugin.front.model.AGTAbstractShowVO.IFType;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.handler.MessageNoticeHandler;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.model.TestCaseDataModel;
import lazy.test.plugin.fundation.processor.TestCaseGenerateProcessor;
import lazy.test.plugin.fundation.processor.TestModelConvertProcessor;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.Wizard;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class AGTPerformanceShowWizard extends Wizard {

    private AGTApplicationContext context;

    public AGTPerformanceShowWizard(AGTApplicationContext context) {
        this.context = context;

        this.setWindowTitle(AGTCommonProperties.WIZARD_WINDOW_TITLE);
        this.addPage(new AGTPerformanceShowPage(AGTPerformanceShowPage.class.getName(), AGTCommonProperties.WIZARD_PAGE_TITLE, context));
    }

    @Override
    public boolean performFinish() {
        if (!context.getVo().validate()) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                TestCaseDataModel testPerformanceModel = TestModelConvertProcessor.parsePerformanceTestModel(context);
                TestCaseDataModel testDubboConfigModel = TestModelConvertProcessor.parseDubboConfigModel(context);
                TestCaseDataModel testJsfConfigModel = TestModelConvertProcessor.parseJsfConfigModel(context);

                context.getTestCaseDataModelList().add(testPerformanceModel);

                if (((AGTPerformanceShowVO)context.getVo()).getIfType().equals(IFType.DUBBO)) {
            		context.getTestCaseDataModelList().add(testDubboConfigModel);
            	} else if (((AGTPerformanceShowVO)context.getVo()).getIfType().equals(IFType.JSF)) {
            		context.getTestCaseDataModelList().add(testJsfConfigModel);
            	}
                
                TestCaseGenerateProcessor.genTestCaseInfo(context);

                IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(testPerformanceModel.getGenProjectName());
                try {
                    project.refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                    MessageNoticeHandler.showExceptionWindow("代码刷新失败", e);
                }
            }
        }.start();

        MessageNoticeHandler.showInfoWindow("测试代码创建成功");
        return true;
    }

}
