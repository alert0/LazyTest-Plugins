package lazy.test.plugin.front.action;

import lazy.test.plugin.front.model.AGTHttpPerformanceShowVO;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.MessageNoticeHandler;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.processor.WizardDialogGenProcessor;
import lazy.test.plugin.fundation.processor.WizardVOConvertProcessor;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Created by wyzhouqiang on 2015/11/21.
 */
public class AGTGenHttpPerformanceTestAction implements IObjectActionDelegate {
	
	private static final String ACTION_ID = "lazy.test.plugin.popmenu.extend.action.package.performance";

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

        AGTApplicationContext context = new AGTApplicationContext();
        try {
            AGTHttpPerformanceShowVO detailShowVO = WizardVOConvertProcessor.parseAGTHttpPerformanceShowVO(selection);
            context.setVo(detailShowVO);

            WizardDialog dialog = WizardDialogGenProcessor.genHttpPerformanceWizardDialog(context, workbench);
            dialog.setMinimumPageSize(100, 400);
            dialog.create();
            dialog.open();
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
