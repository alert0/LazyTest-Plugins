package lazy.test.plugin.front.listener;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

import java.lang.reflect.Field;

import lazy.test.plugin.front.model.AGTAbstractShowVO;
import lazy.test.plugin.front.validator.AGTDetailShowValidator;
import lazy.test.plugin.fundation.exception.AGTBizException;

/**
 * Created by wyzhouqiang on 2015/11/24.
 */
public class AGTTextModifyListener implements ModifyListener {

    private WizardPage page;

    private AGTAbstractShowVO vo;

    private String attrName;

    public AGTTextModifyListener(WizardPage page, AGTAbstractShowVO vo, String attrName) {
        this.page = page;
        this.vo = vo;
        this.attrName = attrName;
    }

    @Override
    public void modifyText(ModifyEvent modifyEvent) {
        Object eventObj = modifyEvent.getSource();
        if (eventObj instanceof Text) {
            try {
                writeDeclaredField(vo, attrName, ((Text) eventObj).getText());
            } catch (Exception e) {
                throw new AGTBizException("调用设值功能错误", "EXP00010", e);
            }
        }

        AGTDetailShowValidator.validateModification(page, vo);
    }

    private void writeDeclaredField(AGTAbstractShowVO vo, String attrName, String text) throws IllegalAccessException {
        Field field = FieldUtils.getField(vo.getClass(), attrName, true);
        FieldUtils.writeField(field, vo, text, true);
    }


}
