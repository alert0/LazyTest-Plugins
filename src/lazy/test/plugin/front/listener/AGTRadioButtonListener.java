package lazy.test.plugin.front.listener;

import lazy.test.plugin.front.model.AGTAbstractShowVO.IFType;
import lazy.test.plugin.front.view.AGTDefaultShowPage;
import lazy.test.plugin.front.view.AGTPerformanceShowPage;
import lazy.test.plugin.front.view.AGTThreadShowPage;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * Created by wyzhouqiang on 2015/11/24.
 */
public class AGTRadioButtonListener implements SelectionListener {

    private AGTDefaultShowPage agtDefaultShowPage;
    
    private AGTThreadShowPage agtThreadShowPage;
    
    private AGTPerformanceShowPage agtPerformanceShowPage;

    private Button button;

    public AGTRadioButtonListener(AGTDefaultShowPage agtDefaultShowPage, Button button) {
        this.agtDefaultShowPage = agtDefaultShowPage;
        this.button = button;
    }
    
    public AGTRadioButtonListener(AGTThreadShowPage agtThreadShowPage, Button button) {
        this.agtThreadShowPage = agtThreadShowPage;
        this.button = button;
    }
    
    public AGTRadioButtonListener(AGTPerformanceShowPage agtPerformanceShowPage, Button button) {
        this.agtPerformanceShowPage = agtPerformanceShowPage;
        this.button = button;
    }
    
    @Override
    public void widgetSelected(SelectionEvent event) {
    	if (button.getSelection()) {
	    	setIFType();
    	}
    }
    
    private void setIFType() {
    	IFType ifType;
    	
    	if (button.getText().equals("DUBBO")) {
    		ifType = IFType.DUBBO;
    	} else {
    		ifType = IFType.JSF;
    	}
    	
    	if (agtDefaultShowPage != null) {
    		agtDefaultShowPage.getVo().setIfType(ifType);
    	} else if (agtThreadShowPage != null) {
    		agtThreadShowPage.getVo().setIfType(ifType);
    	} else {
    		agtPerformanceShowPage.getVo().setIfType(ifType);
    	}
    }

	@Override
	public void widgetDefaultSelected(SelectionEvent arg0) {
		
	}
}
