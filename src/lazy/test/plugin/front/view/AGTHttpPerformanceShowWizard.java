package lazy.test.plugin.front.view;

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
public class AGTHttpPerformanceShowWizard extends Wizard {

    private AGTApplicationContext context;

    public AGTHttpPerformanceShowWizard(AGTApplicationContext context) {
        this.context = context;

        this.setWindowTitle(AGTCommonProperties.WIZARD_WINDOW_TITLE);
        this.addPage(new AGTHttpPerformanceShowPage(AGTHttpPerformanceShowPage.class.getName(), AGTCommonProperties.WIZARD_PAGE_TITLE, context));
    }

    @Override
    public boolean performFinish() {
        if (!context.getVo().validate()) {
            return false;
        }

        new Thread() {
            @Override
            public void run() {
                TestCaseDataModel testPerformanceModel = TestModelConvertProcessor.parseHttpPerformanceTestModel(context);
                context.getTestCaseDataModelList().add(testPerformanceModel);

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
