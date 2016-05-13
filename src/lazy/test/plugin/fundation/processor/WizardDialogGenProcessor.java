package lazy.test.plugin.fundation.processor;

import lazy.test.plugin.front.view.AGTDefaultShowWizard;
import lazy.test.plugin.front.view.AGTHttpPerformanceShowWizard;
import lazy.test.plugin.front.view.AGTPerformanceShowWizard;
import lazy.test.plugin.front.view.AGTThreadShowWizard;
import lazy.test.plugin.fundation.model.AGTApplicationContext;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbench;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class WizardDialogGenProcessor {

    public static WizardDialog genDefaultWizardDialog(AGTApplicationContext context, IWorkbench workbench) {
        AGTDefaultShowWizard wizard = new AGTDefaultShowWizard(context);
        WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow().getShell(), wizard);
        return dialog;
    }

    public static WizardDialog genThreadWizardDialog(AGTApplicationContext context, IWorkbench workbench) {
        AGTThreadShowWizard wizard = new AGTThreadShowWizard(context);
        WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow().getShell(), wizard);
        return dialog;
    }

    public static WizardDialog genPerformanceWizardDialog(AGTApplicationContext context, IWorkbench workbench) {
        AGTPerformanceShowWizard wizard = new AGTPerformanceShowWizard(context);
        WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow().getShell(), wizard);
        return dialog;
    }

    public static WizardDialog genHttpPerformanceWizardDialog(AGTApplicationContext context, IWorkbench workbench) {
        AGTHttpPerformanceShowWizard wizard = new AGTHttpPerformanceShowWizard(context);
        WizardDialog dialog = new WizardDialog(workbench.getActiveWorkbenchWindow().getShell(), wizard);
        return dialog;
    }
}
