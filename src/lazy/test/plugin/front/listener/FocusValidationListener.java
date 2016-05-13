package lazy.test.plugin.front.listener;

import lazy.test.plugin.front.model.AGTAbstractShowVO;
import lazy.test.plugin.front.validator.AGTDetailShowValidator;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;

/**
 * Created by wyzhouqiang on 2015/11/25.
 */
public class FocusValidationListener implements FocusListener {

    private WizardPage page;

    private AGTAbstractShowVO vo;

    public FocusValidationListener(WizardPage page, AGTAbstractShowVO vo) {
        this.page = page;
        this.vo = vo;
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        AGTDetailShowValidator.validateModification(page, vo);
    }

    @Override
    public void focusLost(FocusEvent focusEvent) {
        AGTDetailShowValidator.validateModification(page, vo);
    }
}
