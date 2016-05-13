package lazy.test.plugin.fundation.runner;

import au.com.bytecode.opencsv.CSVWriter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lazy.test.plugin.front.model.AGTTestClassInfoVO;
import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.ExpressCSVTransferHandler;
import lazy.test.plugin.fundation.handler.FileTransferHandler;
import lazy.test.plugin.fundation.handler.VelocityTransferHandler;
import lazy.test.plugin.fundation.model.ParamMetaDataModel;
import lazy.test.plugin.fundation.model.TestCaseDataModel;
import lazy.test.plugin.fundation.model.TestResDataModel;

/**
 * Created by wyzhouqiang on 2016/1/4.
 */
public abstract class ExpressAbstractTestGenRunner extends AGTRunner {

    protected ExpressAbstractTestGenRunner(TestCaseDataModel model) {
        super(model);
    }

    protected void writeTestCaseSource() {
        Map<String, Object> velocityMap = prepareVelocityMap(model);
        String source = VelocityTransferHandler.generateTestCase(model.getGenProjectPath(), model.getGenTestCaseTemplate(), velocityMap);

        String fileFolder = StringUtils.substringBeforeLast(model.getGenTestCodePath(), "/");
        File sourceFolder = new File(fileFolder);
        if (!sourceFolder.exists()) {
            boolean flag = sourceFolder.mkdirs();
            if (!flag) {
                throw new AGTBizException("测试文件生成失败", "EXP00004");
            }
        }
        FileTransferHandler.copyFileFromFileSystem(new ByteArrayInputStream(source.getBytes()), new File(model.getGenTestCodePath()));
    }

    protected void writeTestCaseData() {
        List<TestResDataModel[]> list = ExpressCSVTransferHandler.generateTestData(model);
        List<String[]> result = transferCSVData(list);
        String fileFolder = StringUtils.substringBeforeLast(model.getGenTestDataPath(), "/");
        File sourceFolder = new File(fileFolder);
        if (!sourceFolder.exists()) {
            boolean flag = sourceFolder.mkdirs();
            if (!flag) {
                throw new AGTBizException("测试文件生成失败", "EXP00004");
            }
        }
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;
        CSVWriter csvWriter = null;
        try {
            fos = new FileOutputStream(new File(model.getGenTestDataPath()));
            osw = new OutputStreamWriter(fos, "GBK");
            csvWriter = new CSVWriter(osw, ',');
            csvWriter.writeAll(result);
            csvWriter.flush();
        } catch (Exception e) {
            throw new AGTBizException("测试文件生成失败", "EXP00004", e);
        } finally {
            IOUtils.closeQuietly(csvWriter);
            IOUtils.closeQuietly(osw);
            IOUtils.closeQuietly(fos);
        }
    }

    private List<String[]> transferCSVData(List<TestResDataModel[]> list) {
        List<String[]> result = new ArrayList<String[]>();
        for (TestResDataModel[] resDataArray : list) {
            String [] strArray = new String[resDataArray.length];
            for (int i = 0; i < resDataArray.length; i++) {
                strArray[i] = resDataArray[i].getResDataValue();
            }
            result.add(strArray);
        }
        return result;
    }

    private Map<String, Object> prepareVelocityMap(TestCaseDataModel model) {
        Map<String, Object> map = new HashMap<String, Object>();
        AGTTestClassInfoVO classInfo = new AGTTestClassInfoVO();
        classInfo.setGenPackageName(model.getGenPackageName());
        classInfo.setGenImportList(model.getGenImportList());
        classInfo.setGenClassName(model.getGenClassName());
        classInfo.setGenMethodName(model.getGenMethodName());
        if (StringUtils.isNotBlank(model.getTestMethodParamStr())) {
            classInfo.setGenMethodParam(StringUtils.strip(model.getTestMethodParamStr()));
        }
        String configPrefix = model.getGenProjectPath() + "/" + AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX;
        classInfo.setGenConfigPath(StringUtils.substringBeforeLast(StringUtils.replace(model.getGenTestDataPath(), configPrefix, ""), "/"));

        classInfo.setTestClassName(model.getTestClassName());
        classInfo.setTestClassVar(StringUtils.uncapitalize(model.getTestClassName()));
        classInfo.setTestMethodName(model.getTestMethodName());
        classInfo.setTestMethodParam(StringUtils.strip(model.getTestMethodParamStr()));
        if (StringUtils.isNotBlank(model.getTestReturnObject().getReturnStr()) && !StringUtils.equals(model.getTestReturnObject().getReturnStr(), "void")) {
            classInfo.setTestReturnObject(model.getTestReturnObject().getReturnStr());
        }

        StringBuilder buffer = new StringBuilder();
        for (ParamMetaDataModel param : model.getTestParamList()) {
            buffer.append(param.getParamName()).append(", ");
        }
        if (StringUtils.isNotBlank(buffer.toString())) {
            classInfo.setTestInvokeParam(buffer.substring(0, buffer.length() - 2));
        }

        List<Integer> testNumList = new ArrayList<Integer>();
        for (int i = 1; i <= model.getGenTestCaseNum(); i++) {
            testNumList.add(i);
        }

        map.put(AGTCommonProperties.TEST_VELOCITY_CLASS_INFO_KEY, classInfo);
        return map;
    }
}
