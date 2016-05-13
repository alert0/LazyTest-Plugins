package lazy.test.plugin.fundation.handler;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class MessageConfigHandler {

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
            synchronized (MessageConfigHandler.class) {
                if (properties == null) {
                    properties = new Properties();
                }
            }
        }
        if (properties.isEmpty()) {
            synchronized (MessageConfigHandler.class) {
                if (properties.isEmpty()) {
                    try {
                        InputStream inputStream = MessageConfigHandler.class.getClassLoader().getResourceAsStream("config.properties");
                        properties.load(inputStream);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
