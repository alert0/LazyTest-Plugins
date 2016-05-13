package lazy.test.plugin.fundation.runner;

import java.util.ArrayList;
import java.util.List;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.MessageNoticeHandler;
import lazy.test.plugin.fundation.model.AGTApplicationContext;

/**
 * Created by wyzhouqiang on 2015/12/3.
 */
public class AGTRunnerSuit {

    private List<AGTRunner> runnerList;

    private AGTApplicationContext context;

    public AGTRunnerSuit(AGTApplicationContext context) {
        this.context = context;
        runnerList = new ArrayList<AGTRunner>();
    }

    public void addRunner(AGTRunner runner) {
        runnerList.add(runner);
    }

    public void start() {
        for (final AGTRunner runner : runnerList) {
            if (runner == null) {
                continue;
            }
            try {
                runner.init(context);
                runner.run();
            } catch (AGTBizException e) {
                MessageNoticeHandler.showExceptionWindow(e.getCode(), e);
            } catch (Exception e) {
                MessageNoticeHandler.showExceptionWindow(AGTCommonProperties.DEFAULT_EXCEPTION, e);
            }
        }
    }
}
