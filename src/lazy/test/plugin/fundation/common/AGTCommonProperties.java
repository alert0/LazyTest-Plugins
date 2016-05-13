package lazy.test.plugin.fundation.common;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public interface AGTCommonProperties {

    String DEFAULT_DATE_FORMAT = "yyyy/MM/dd";

    String DEFAULT_DATA_ENCODE = "UTF-8";

    String DEFAULT_EXCEPTION = "EXP00000";

    String PROJECT_TEST_TEMPLATE_SKIP = "TEST_CASE_TEMPLATE_SKIP";

    String PROJECT_TEST_TYPE_DEFAULT_NORMAL = "NORMAL_TYPE_DEFAULT_TEMPLATE";

    String PROJECT_TEST_TYPE_DEFAULT_EXCEPTION = "EXCEPTION_TYPE_DEFAULT_TEMPLATE";

    String PROJECT_TEST_TYPE_DEFAULT_PARAM = "PARAM_TYPE_DEFAULT_TEMPLATE";

//    String PROJECT_TEST_TYPE_DEFAULT_CONCURRENT = "CONCURRENT_TYPE_DEFAULT_TEMPLATE";
//
//    String PROJECT_TEST_TYPE_JMETER_TEMPLATE = "PERFORMANCE_TYPE_JMETER_TEMPLATE";

    String PROJECT_TEST_TYPE_DUBBO_CONFIG_RUNNER="DUBBO_CONFIG_GEN_RUNNER";
    
    String PROJECT_TEST_TYPE_JSF_CONFIG_RUNNER="JSF_CONFIG_GEN_RUNNER";

    /**AGTApplicationContext*/
    String CONTEXT_USER_DATA_KEY_PROJECT = "CONTEXT_USER_DATA_KEY_PROJECT"; //Value: IJavaProject

    String CONTEXT_USER_DATA_KEY_PROJECT_BUILD_PATH = "CONTEXT_USER_DATA_KEY_PROJECT_BUILD_PATH"; //Value: String

    String CONTEXT_USER_DATA_KEY_URL_CLASS_LOADER = "CONTEXT_USER_DATA_KEY_URL_CLASS_LOADER"; //UrlClassLoader

    /**WizardView*/
    String WIZARD_WINDOW_TITLE = "自动化测试工具";

    String WIZARD_PAGE_TITLE = "测试用例选项";

    String WIZARD_MSG_UNKNOWN = "未知测试错误";

    String WIZARD_MSG_TEST_EXIST = "测试用例类名已存在";

    String WIZARD_MSG_BLANK_HTTP_URL = "请输入测试Url地址";

    String WIZARD_MSG_BLANK_FORMAT_URL = "测试Url地址必须以http开头";

    String WIZARD_MSG_BLANK_PACKAGE = "测试包名不能为空";

    String WIZARD_MSG_MODIFY_PACKAGE = "请修改测试包名";

    String WIZARD_MSG_ERROR_PACKAGE = "测试包名格式不正确";

    String WIZARD_MSG_BLANK_CLASS = "测试类名不能为空";

    String WIZARD_MSG_MODIFY_CLASS = "请修改测试类名";

    String WIZARD_MSG_BLANK_METHOD = "测试方法名不能为空";

    String WIZARD_MSG_BLANK_TESTNUM = "测试用例数量不能为空";

    String WIZARD_MSG_NUMBERIC_CONFIG = "测试参数只能为数字";

    String WIZARD_MSG_MORE_TESTNUM = "测试用例数量需大于1";

    String WIZARD_MSG_MORE_THREAD_SIZE = "测试线程数量不得少于最小线程数";

    String WIZARD_MSG_MORE_THREAD_INVOKE = "测试并发总次数需大于线程数量";

    String WIZARD_MSG_THREAD_POOL_SIZE_BLANK = "测试并发线程数不能为空";

    String WIZARD_MSG_THREAD_INVOKE_COUNT_BLANK = "测试并发运行次数不能为空";

    String WIZARD_MSG_THREAD_SETUP_TIME_BLANK = "性能测试线程启动时间不能为空";

    /**ClassMetaData*/
    String AST_PARSE_TYPE_SIMPLE = "AST_PARSE_TYPE_SIMPLE";

    String AST_PARSE_TYPE_PRIMITIVE = "AST_PARSE_TYPE_PRIMITIVE";

    String AST_PARSE_TYPE_ARRAY = "AST_PARSE_TYPE_ARRAY";

    String AST_PARSE_TYPE_PARAM = "AST_PARSE_TYPE_PARAM";

    String AST_PARSE_KEY_PARAM_CLASS = "AST_PARSE_KEY_PARAM_CLASS";

    String AST_PARSE_KEY_RAW_STR = "AST_PARSE_KEY_RAW_STR";

    /**GenTestCase*/
    String TEST_VELOCITY_AUTHOR_KEY = "author";

    String TEST_VELOCITY_TIME_KEY = "time";

    String TEST_VELOCITY_CLASS_INFO_KEY = "classInfo";

    String TEST_VELOCITY_HTTP_URL = "httpUrl";

    String TEST_VELOCITY_TEST_CODE_KEY = "testCode";

    String TEST_VELOCITY_TEST_NUM_KEY = "testNumList";

    String TEST_VELOCITY_THREAD_POOL_SIZE_KEY = "threadPoolSize";

    String TEST_VELOCITY_THREAD_INVOCATION_COUNT_KEY = "invocationCount";

    String TEST_VELOCITY_JMETER_THREAD_NUM_KEY = "totalThreadNum";

    String TEST_VELOCITY_JMETER_THREAD_LOOP_TIMES_KEY = "singleThreadLoopTimes";

    String TEST_VELOCITY_JMETER_THREAD_SETUP_TIME_KEY = "totalThreadSetupTime";

    String PROJECT_TEST_ROOT_PATH_PREFIX = "src/test/";

    String PROJECT_TEST_CODE_PATH_PREFIX = "src/test/java/";

    String PROJECT_TEST_RES_PATH_PREFIX = "src/test/resources/";

    String PROJECT_TEST_TEMPLATE_PREFIX = "vms/";

    String PROJECT_TEST_CONFIGURE_PREFIX = "tcfg/";

    String PROJECT_TEST_INPUT_PARAM = "String testRun, String testCode, String testDesc";

    /**GenTestData*/
    String TEST_DATA_DEFAULT_HEAD_TEST_RUN = "是否执行[TEST_RUN]";

    String TEST_DATA_DEFAULT_HEAD_TEST_CODE = "测试编号[TEST_CODE]";

    String TEST_DATA_DEFAULT_HEAD_TEST_DESC = "测试描述[TEST_DESC]";

    String TEST_DATA_EXPRESS_HEAD_CLASS_NAME = "类名全路径[CLASS_NAME]";

    String TEST_DATA_EXPRESS_HEAD_METHOD_NAME = "测试方法名[METHOD_NAME]";

    String TEST_DATA_EXPRESS_HEAD_PARAM_NAME = "参数类名全路径[PARAM_NAME]";

    String TEST_DATA_EXPRESS_HEAD_BEAN_ID = "容器BeanID[BEAN_ID]";

    String TEST_DATA_EXPRESS_HEAD_DEFAULT_KEY = "特殊值key[DEFAULT_KEY]";

    String TEST_DATA_EXPRESS_HEAD_DEFAULT_VALUE = "特殊值value[DEFAULT_VALUE]";

    String TEST_DATA_EXPRESS_HEAD_RUN_FLAG = "是否执行[RUN_FLAG]";

    String TEST_DATA_EXPRESS_HEAD_SET_KEY = "参数key[SET_KEY]";

    String TEST_DATA_EXPRESS_HEAD_SET_VALUE = "参数value[SET_VALUE]";

    String TEST_DATA_EXPRESS_HEAD_RESULT_KEY = "结果key[RESULT_KEY]";

    String TEST_DATA_EXPRESS_HEAD_RESULT_VALUE = "结果value[RESULT_VALUE]";

    /**ConfigureKey*/
    String PROJECT_CONFIG_BASE_GEN_POM = "test.base.gen.pom"; //测试基础pom文件
    
    String PROJECT_CONFIG_BASE_GEN_SPRING = "test.base.gen.spring.list"; //测试基础pom文件

    String PROJECT_CONFIG_BASE_GEN_DEPENDENCY = "test.base.gen.dependency"; //测试基础pom依赖

    String PROJECT_CONFIG_BASE_PROJECT_GEN_LIST = "test.base.gen.project.list"; //测试基础文件列表

    String PROJECT_CONFIG_BASE_REDIS_GEN_LIST = "test.base.gen.redis.list"; //测试redis模板文件列表

    String PROJECT_CONFIG_BASE_MSP_GEN_LIST = "test.base.gen.msp.list"; //测试msp模板文件列表

    String PROJECT_CONFIG_BASE_DUBBO_GEN_LIST = "test.base.gen.dubbo.list"; //测试dubbo模板文件列表
    
    String PROJECT_CONFIG_BASE_JSF_GEN_LIST = "test.base.gen.jsf.list"; //测试jsf模板文件列表

    String PROJECT_CONFIG_BASE_TEMPLATE_GEN_LIST = "test.base.gen.template.list"; //测试模板文件列表

    String PROJECT_CONFIG_TEMPLATE_GEN_NORMAL_LIST = "test.template.gen.normal.list"; //正常测试模板选择项

    String PROJECT_CONFIG_TEMPLATE_GEN_EXCEPTION_LIST = "test.template.gen.exception.list"; //异常测试模板选择项

    String PROJECT_CONFIG_TEMPLATE_GEN_PARAM_LIST = "test.template.gen.param.list"; //参数测试模板选择项

    String PROJECT_CONFIG_PERFORMANCE_GEN_POM = "test.performance.gen.pom"; //性能测试pom文件

    String PROJECT_CONFIG_PERFORMANCE_PROJECT_GEN_LIST = "test.performance.gen.project.list"; //性能测试文件列表
    
    String PROJECT_CONFIG_PERFORMANCE_DUBBO_GEN_LIST = "test.performance.gen.dubbo.list"; //测试dubbo模板文件列表
    
    String PROJECT_CONFIG_PERFORMANCE_JSF_GEN_LIST = "test.performance.gen.jsf.list"; //测试jsf模板文件列表

    /**Plugin default config*/
    String PROJECT_CONFIG_GEN_SKIP_NORMAL = "plugin.config.gen.skip.normal"; //是否跳过生成normalTest

    String PROJECT_CONFIG_GEN_SKIP_EXCEPTION = "plugin.config.gen.skip.exception"; //是否跳过生成exceptionTest

    String PROJECT_CONFIG_GEN_SKIP_PARAM = "plugin.config.gen.skip.param"; //是否跳过生成paramTest

    String PROJECT_CONFIG_GEN_TYPE_NORMAL = "plugin.config.gen.type.normal"; //生成normalTest类型

    String PROJECT_CONFIG_GEN_TYPE_EXCEPTION = "plugin.config.gen.type.exception"; //生成paramTest类型

    String PROJECT_CONFIG_GEN_TYPE_PARAM = "plugin.config.gen.type.param"; //生成exceptionTest类型

    String PROJECT_CONFIG_GEN_TYPE_CONCURRENT = "plugin.config.gen.type.concurrent"; //生成concurrent测试类型

    String PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE = "plugin.config.gen.type.performance"; //生成性能测试类型

    String PROJECT_CONFIG_GEN_TYPE_PEROFRMANCE_HTTP = "plugin.config.gen.type.performance.http"; //生成HTTP性能测试类型

    String PROJECT_CONFIG_GEN_VALUE_CONCURRENT_MIN_THREAD = "plugin.config.gen.value.concurrent.min.thread"; //并发测试最小线程数

    String PROJECT_CONFIG_GEN_VALUE_SHOW_NEW_TEMPLATE = "plugin.config.gen.value.show.new.template"; //是否开启生成新模板功能

    String PROJECT_CONFIG_GEN_VALUE_CREATE_REDIS = "plugin.config.gen.value.create.redis"; //是否生成redis模块

    String PROJECT_CONFIG_GEN_VALUE_CREATE_MSP = "plugin.config.gen.value.create.msp"; //是否生成msp模块

    String PROJECT_CONFIG_GEN_VALUE_CREATE_IFREG = "plugin.config.gen.value.create.ifreg"; //是否生成接口注册模块

    String PROJECT_CONFIG_WHITEBOX = "plugin.whitebox"; //是否为白盒版本
}
