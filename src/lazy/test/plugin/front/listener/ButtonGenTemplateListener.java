package lazy.test.plugin.front.listener;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

import java.io.File;

import lazy.test.plugin.front.model.AGTAbstractShowVO;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.FileTransferHandler;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;
import lazy.test.plugin.fundation.handler.MessageNoticeHandler;

/**
 * Created by wyzhouqiang on 2015/12/9.
 */
public class ButtonGenTemplateListener implements SelectionListener {

    private AGTAbstractShowVO vo;

    public ButtonGenTemplateListener(AGTAbstractShowVO vo) {
        this.vo = vo;
    }

    @Override
    public void widgetSelected(SelectionEvent selectionEvent) {
        genCustomerTemplate();
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent selectionEvent) {
        genCustomerTemplate();
    }

    private void genCustomerTemplate() {
        String customerPath = vo.getTarProjectPath() + "/" + AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX;
        File filePath = new File(customerPath);
        if (!filePath.exists()) {
            boolean flag = filePath.mkdirs();
            if (!flag) {
                throw new AGTBizException("创建模板失败", "EXP00008");
            }
        }

        String rawList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_TEMPLATE_GEN_LIST);
        for (String fileName : StringUtils.split(rawList, ",")) {
            fileName = StringUtils.strip(fileName);
            String srcName = AGTCommonProperties.PROJECT_TEST_TEMPLATE_PREFIX + fileName;
            String tarName = vo.getTarProjectPath() + "/" + MessageConfigHandler.findMappingResult(fileName);
            File tarFile = new File(tarName);
            if (tarFile.exists()) {
                MessageNoticeHandler.showInfoWindow("已存在相同模板: customer-class.vm");
                return;
            }
            FileTransferHandler.copyFileFromClassPath(srcName, tarFile);
        }

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(vo.getTarProjectName());
        try {
            project.refreshLocal(IResource.DEPTH_INFINITE, null);
        } catch (CoreException e) {
            throw new AGTBizException("代码刷新失败", "EXP00005", e);
        }

        MessageNoticeHandler.showInfoWindow("测试模板生成成功");
    }
}
