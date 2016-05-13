package lazy.test.plugin.fundation.handler;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import lazy.test.plugin.fundation.exception.AGTBizException;

/**
 * Created by wyzhouqiang on 2015/12/30.
 */
public class FileTransferHandler {

    public static void copyFileFromClassPath(String srcFileName, File tarFile) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = FileTransferHandler.class.getClassLoader().getResourceAsStream(srcFileName);
            os = new FileOutputStream(tarFile);
            IOUtils.copy(is, os);
        } catch (Exception e) {
            throw new AGTBizException("创建文件失败", "EXP00000", e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
    }

    public static void copyFileFromFileSystem(InputStream is, File tarFile) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(tarFile);
            IOUtils.copy(is, os);
        } catch (Exception e) {
            throw new AGTBizException("复制文件失败", "EXP00000", e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
    }
}
