package lazy.test.plugin.fundation.processor;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jface.viewers.IStructuredSelection;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.model.AGTApplicationContext;

/**
 * Created by wyzhouqiang on 2015/12/3.
 */
public class ASTProjectLoadProcessor {

    public static void loadProjectResource(final AGTApplicationContext context, IStructuredSelection selection) {
        IMember member = (IMember)selection.getFirstElement();
        IJavaProject project = member.getJavaProject();
        try {
            String buildPath = project.getOutputLocation().toString();
            String[] importArray = JavaRuntime.computeDefaultRuntimeClassPath(project);
            context.putUserData(AGTCommonProperties.CONTEXT_USER_DATA_KEY_PROJECT, project);
            context.putUserData(AGTCommonProperties.CONTEXT_USER_DATA_KEY_PROJECT_BUILD_PATH, buildPath);

            URL[] urlArray = getUrlArray(importArray);
            URLClassLoader loader = new URLClassLoader(urlArray);
            context.putUserData(AGTCommonProperties.CONTEXT_USER_DATA_KEY_URL_CLASS_LOADER, loader);
        } catch (Exception e) {
            throw new AGTBizException("获取项目环境信息失败", "EXP00006", e);
        }
    }

    private static URL[] getUrlArray(String[] importArray) throws Exception{
        URL[] urlArray = new URL[importArray.length];
        for (int i = 0; i < importArray.length; i++) {
            File file = new File(importArray[i]);
            urlArray[i] = file.toURI().toURL();
        }
        return urlArray;
    }
}
