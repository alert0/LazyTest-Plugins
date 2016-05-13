package lazy.test.plugin.fundation.handler;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;

/**
 * Created by wyzhouqiang on 2015/11/30.
 */
public class VelocityTransferHandler {

    private static final Map<String, VelocityEngine> HOLDERS = new HashMap<String, VelocityEngine>();

    private static final SimpleDateFormat sdf = new SimpleDateFormat(AGTCommonProperties.DEFAULT_DATE_FORMAT);

    static {
        Velocity.setProperty("input.encoding", AGTCommonProperties.DEFAULT_DATA_ENCODE);
        Velocity.setProperty("output.encoding", AGTCommonProperties.DEFAULT_DATA_ENCODE);
        Velocity.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        try {
            Velocity.init();
        } catch (Exception e) {
            throw new AGTBizException("Velocity模板解析失败", "EXP00003", e);
        }
    }

    public static String generateTestCase(String projectPath, String templatePath, Map<String, Object> userMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(AGTCommonProperties.TEST_VELOCITY_AUTHOR_KEY, System.getenv("USERNAME"));
        map.put(AGTCommonProperties.TEST_VELOCITY_TIME_KEY, sdf.format(new Date()));
        map.putAll(userMap);

        VelocityContext context = new VelocityContext(map);
        StringWriter sw = new StringWriter();
        try {
            Template template = getTemplate(projectPath, templatePath);
            template.merge(context, sw);
            return sw.toString();
        } catch (Exception e) {
            throw new AGTBizException("Velocity模板解析失败", "EXP00003", e);
        } finally {
            IOUtils.closeQuietly(sw);
        }
    }

    private static Template getTemplate(String projectPath, String templatePath) throws Exception {
        Template template = null;
        File fileCust = new File(projectPath + "/" + templatePath);
        if (fileCust.exists()) {
            templatePath = projectPath + "/" + templatePath;
            String path = StringUtils.substringBeforeLast(templatePath, "/");
            String name = StringUtils.substringAfterLast(templatePath, "/");
            if (!HOLDERS.containsKey(path)) {
                initFileVelocity(path);
            }
            VelocityEngine velocityEngine = HOLDERS.get(path);
            template = velocityEngine.getTemplate(name);
        } else {
            template = Velocity.getTemplate(templatePath);
        }
        return template;
    }

    private static void initFileVelocity(String path) {
        VelocityEngine fileVelocity = new VelocityEngine();
        fileVelocity.setProperty("input.encoding", AGTCommonProperties.DEFAULT_DATA_ENCODE);
        fileVelocity.setProperty("output.encoding", AGTCommonProperties.DEFAULT_DATA_ENCODE);
        fileVelocity.setProperty("file.resource.loader.path", path);
        fileVelocity.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        try {
            fileVelocity.init();
        } catch (Exception e) {
            throw new AGTBizException("Velocity模板解析失败", "EXP00003", e);
        }
        HOLDERS.put(path, fileVelocity);
    }
}
