package common;

import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

@ContextConfiguration(locations ={"classpath*:spring-test.xml"})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
public abstract class BasePerformanceCase extends AbstractJavaSamplerClient implements ApplicationContextAware {

    protected ApplicationContext applicationContext;

    private final TestContextManager testContextManager;

    public BasePerformanceCase() {
        this.testContextManager = new TestContextManager(getClass());
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult result = new SampleResult();
        try {
            testContextManager.beforeTestClass();
            testContextManager.prepareTestInstance(this);
            result = run(javaSamplerContext);
            testContextManager.afterTestClass();
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccessful(false);
        }
        return result;
    }

    public abstract SampleResult run(JavaSamplerContext javaSamplerContext) throws Exception;
}