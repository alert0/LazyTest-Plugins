package lazy.test.plugin.fundation.runner;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.model.TestCaseDataModel;

/**
 * Created by wyzhouqiang on 2016/1/4.
 */
public class DefaultConcurrentTestGenRunner extends DefaultAbstractTestGenRunner {

    private ClassLoader loader;

    public DefaultConcurrentTestGenRunner(TestCaseDataModel model) {
        super(model);
    }

    @Override
    public void init(AGTApplicationContext context) {
        loader = context.getUserData(AGTCommonProperties.CONTEXT_USER_DATA_KEY_URL_CLASS_LOADER);
        if (loader == null) {
            throw new AGTBizException("测试代码生成信息错误", "EXP00009");
        }
    }

    @Override
    public void run() {
        writeTestCaseSource();
        writeTestCaseData(loader);
    }
}
