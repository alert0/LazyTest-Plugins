package lazy.test.plugin.fundation.handler;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class MessageMappingHandler {

    private static Properties properties;

    public static String findMappingResult(String code) {
        initProperties();
        if (properties.containsKey(code)) {
            return properties.getProperty(code);
        }

        return code;
    }

    private static void initProperties() {
        if (properties == null) {
            synchronized (MessageMappingHandler.class) {
                if (properties == null) {
                    properties = new Properties();
                }
            }
        }
        if (properties.isEmpty()) {
            synchronized (MessageMappingHandler.class) {
                if (properties.isEmpty()) {
                    try {
                        InputStream inputStream = MessageMappingHandler.class.getClassLoader().getResourceAsStream("lazy/test/plugin/mapping.properties");
                        properties.load(inputStream);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
