package lazy.test.plugin.front.action;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;

import java.io.File;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.MessageNoticeHandler;
import lazy.test.plugin.fundation.processor.TestBaseGenerateProcessor;

/**
 * Created by wyzhouqiang on 2015/12/12.
 */
public class AGTGenProjectBaseAction implements IObjectActionDelegate {

    private static final String ACTION_ID = "lazy.test.plugin.popmenu.extend.action.project.base";

    private IStructuredSelection selection;

    private IWorkbench workbench;

    @Override
    public void run(IAction action) {
        if (action == null || selection == null || workbench == null) {
            return;
        }
        if (!StringUtils.equals(action.getId(), ACTION_ID)) {
            return;
        }

        try {
            IProject project = (IProject)selection.getFirstElement();
            String tarProjectPath = new File(project.getLocationURI()).getAbsolutePath();
            boolean flag = TestBaseGenerateProcessor.generateBaseTestConfigure(tarProjectPath);
            
            if (flag) {
            	MessageNoticeHandler.showInfoWindow("测试基础模块创建成功");
                project.getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
            }
        } catch (AGTBizException e) {
            MessageNoticeHandler.showExceptionWindow(e.getCode(), e);
        } catch (Exception e) {
            MessageNoticeHandler.showExceptionWindow(AGTCommonProperties.DEFAULT_EXCEPTION, e);
        }
    }

    @Override
    public void selectionChanged(IAction action, ISelection selection) {
        if (selection instanceof IStructuredSelection) {
            this.selection = (IStructuredSelection) selection;
        }
    }

    @Override
    public void setActivePart(IAction action, IWorkbenchPart workbenchPart) {
        this.workbench = workbenchPart.getSite().getWorkbenchWindow().getWorkbench();
    }
}
