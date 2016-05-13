package lazy.test.plugin.fundation.runner;

import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.model.TestCaseDataModel;

/**
 * Created by wyzhouqiang on 2015/12/3.
 */
public abstract class AGTRunner {

    protected TestCaseDataModel model;

    protected AGTRunner(TestCaseDataModel model) {
        this.model = model;
        if (model == null) {
            throw new AGTBizException("测试代码生成信息错误", "EXP00009");
        }
    }

    public abstract void init(AGTApplicationContext context);

    public abstract void run();
}
