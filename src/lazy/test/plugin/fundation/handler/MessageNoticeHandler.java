package lazy.test.plugin.fundation.handler;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;

import java.io.InputStream;
import java.util.Properties;

import lazy.test.plugin.front.Activator;

/**
 * Created by wyzhouqiang on 2015/11/23.
 */
public class MessageNoticeHandler {

    private static Properties properties;

    public static void showExceptionWindow(String code, Throwable e) {
        Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "AGT EXCEPTION", e));

        initProperties();
        String errorMsg = "系统错误";
        if (properties.containsKey(code)) {
            errorMsg = properties.getProperty(code);
        }
        MessageDialog.openError(null, "AGT EXCEPTION", errorMsg);
    }

    public static boolean showQuestionWindow(String msg) {
        return MessageDialog.openQuestion(null, "AGT CONFIRM", msg);
    }
    
    public static int showQuestionWindowWithCancel(String msg) {
    	MessageDialog msgDialog = new MessageDialog(null, "AGT CONFIRM", MessageDialog.getDefaultImage(), msg, MessageDialog.QUESTION_WITH_CANCEL, new String[]{"合并", "覆盖", "取消"}, 0);
    	return msgDialog.open();
    }
    
    public static int showQuestionWindow(String msg, String[] options) {
    	MessageDialog msgDialog = new MessageDialog(null, "AGT CONFIRM", MessageDialog.getDefaultImage(), msg, MessageDialog.QUESTION, options, 0);
    	return msgDialog.open();
    }

    public static boolean showConfirmWindow(String msg) {
        return MessageDialog.openConfirm(null, "AGT CONFIRM", msg);
    }

    public static void showInfoWindow(String msg) {
        MessageDialog.openInformation(null, "AGT INFO", msg);
    }

    public static void showWarningWindow(String msg) {
        MessageDialog.openWarning(null, "AGT WARNING", msg);
    }

    public static void showErrorWindow(String msg) {
        MessageDialog.openError(null, "AGT ERROR", msg);
    }

    public static void logError(String msg) {
        Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "AGT EXCEPTION: " + msg, null));
    }

    public static void logError(String msg, Throwable e) {
        Activator.getDefault().getLog().log(new Status(Status.ERROR, Activator.PLUGIN_ID, "AGT EXCEPTION: " + msg, e));
    }

    private static void initProperties() {
        if (properties == null) {
            synchronized (MessageNoticeHandler.class) {
                if (properties == null) {
                    properties = new Properties();
                }
            }
        }
        if (properties.isEmpty()) {
            synchronized (MessageNoticeHandler.class) {
                if (properties.isEmpty()) {
                    try {
                        InputStream inputStream = MessageNoticeHandler.class.getClassLoader().getResourceAsStream("lazy/test/plugin/exception.properties");
                        properties.load(inputStream);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
