package lazy.test.plugin.fundation.processor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.*;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.handler.MessageConfigHandler;
import lazy.test.plugin.fundation.handler.MessageNoticeHandler;

/**
 * Created by wyzhouqiang on 2015/12/12.
 */
public class TestBaseGenerateProcessor {

    public static boolean generateBaseTestConfigure(String tarProjectPath) {
        /**选择覆盖或者合并pom文件依赖*/
        String pomName = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_GEN_POM);
        String dependencyName = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_GEN_DEPENDENCY);

        InputStream pomInputStream = TestBaseGenerateProcessor.class.getClassLoader().getResourceAsStream(AGTCommonProperties.PROJECT_TEST_CONFIGURE_PREFIX + pomName);
        String pomPath = tarProjectPath + "/" + MessageConfigHandler.findMappingResult(pomName);
        File pomFile = new File(pomPath);
        int needMerge = -1;
        if (pomFile.exists()) {
            needMerge = MessageNoticeHandler.showQuestionWindowWithCancel("项目已存在pom依赖配置，请选择要进行的操作：");
            
            if(needMerge == -1 || needMerge == 2) {
            	return false;
            }
            
            backUpTargetFile(pomFile);
        } else {
        	throw new AGTBizException("创建模板失败", "EXP00012");
        }
        
        if (needMerge == 0) {
            InputStream dependencyInputStream = TestBaseGenerateProcessor.class.getClassLoader().getResourceAsStream(AGTCommonProperties.PROJECT_TEST_CONFIGURE_PREFIX + dependencyName);
            pomInputStream = mergePomConfig(dependencyInputStream, pomFile);
            pomInputStream = formatPomConfig(pomInputStream);
        } else {
            pomInputStream = replacePomConfig(pomInputStream, pomFile);
        }
        copyBaseTestFile(pomInputStream, pomFile);

        /**生成测试基础文件*/
        String rawStr = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_PROJECT_GEN_LIST);
        createTestFile(tarProjectPath, rawStr);

        /**生成配置化文件*/
        if (!Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_WHITEBOX))) {
	        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_REDIS))) {
	            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_REDIS_GEN_LIST);
	            createTestFile(tarProjectPath, fileList);
	        }
	        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_MSP))) {
	            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_MSP_GEN_LIST);
	            createTestFile(tarProjectPath, fileList);
	        }
	        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_IFREG))) {
	        	//int projectType = MessageNoticeHandler.showQuestionWindow("请选择项目类型:\n若关闭窗口会默认按Dubbo项目生成测试基础", 
	        	//		new String[]{"Dubbo", "JSF"});
	        	
	        	//if (projectType == 0 || projectType == -1 ) {
		            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_DUBBO_GEN_LIST);
		            createTestFile(tarProjectPath, fileList);
	        	//} else if (projectType == 1) {
	        	//	String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_JSF_GEN_LIST);
		        //    createTestFile(tarProjectPath, fileList);
	        	//}
	        }
        } else {
        	String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_GEN_SPRING);
            createTestFile(tarProjectPath, fileList);
        }

        /**自定义添加目录*/
        File utilDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX + "util");
        if (!utilDir.exists()) {
            utilDir.mkdirs();
        }
        File testDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX + "test");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
        File confDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX + "conf");
        if (!confDir.exists()) {
            confDir.mkdirs();
        }
        File dataDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX + "data");
        if (!dataDir.exists()) {
        	dataDir.mkdirs();
        }
        
        return true;
    }

    public static void generatePerformanceTestConfigure(String tarProjectPath) {
        /**生成pom文件依赖*/
        String pomName = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_PERFORMANCE_GEN_POM);

        InputStream pomInputStream = TestBaseGenerateProcessor.class.getClassLoader().getResourceAsStream(AGTCommonProperties.PROJECT_TEST_CONFIGURE_PREFIX + pomName);
        String pomPath = tarProjectPath + "/" + MessageConfigHandler.findMappingResult(pomName);
        File pomFile = new File(pomPath);
        if (pomFile.exists()) {
            backUpTargetFile(pomFile);
        } else {
        	throw new AGTBizException("创建模板失败", "EXP00012");
        }
        
        pomInputStream = replacePomConfig(pomInputStream, pomFile);
        copyBaseTestFile(pomInputStream, pomFile);

        /**生成测试基础文件*/
        String rawStr = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_PERFORMANCE_PROJECT_GEN_LIST);
        createTestFile(tarProjectPath, rawStr);

        /**生成配置化文件*/
        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_REDIS))) {
            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_REDIS_GEN_LIST);
            createTestFile(tarProjectPath, fileList);
        }
        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_MSP))) {
            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_MSP_GEN_LIST);
            createTestFile(tarProjectPath, fileList);
        }
//        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_IFREG))) {
//            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_BASE_DUBBO_GEN_LIST);
//            createTestFile(tarProjectPath, fileList);
//        }
        if (Boolean.valueOf(MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_GEN_VALUE_CREATE_IFREG))) {
        	//int projectType = MessageNoticeHandler.showQuestionWindow("请选择项目类型:\n若关闭窗口会默认按Dubbo项目生成测试基础", 
        	//		new String[]{"Dubbo", "JSF"});
        	
        	//if (projectType == 0 || projectType == -1 ) {
	            String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_PERFORMANCE_DUBBO_GEN_LIST);
	            createTestFile(tarProjectPath, fileList);
        	//} else if (projectType == 1) {
        	//	String fileList = MessageConfigHandler.findMappingResult(AGTCommonProperties.PROJECT_CONFIG_PERFORMANCE_JSF_GEN_LIST);
	        //    createTestFile(tarProjectPath, fileList);
        	//}
        }

        /**自定义添加目录*/
        File utilDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX + "util");
        if (!utilDir.exists()) {
            utilDir.mkdirs();
        }
        File testDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_CODE_PATH_PREFIX + "test");
        if (!testDir.exists()) {
            testDir.mkdirs();
        }
        File confDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX + "conf");
        if (!confDir.exists()) {
            confDir.mkdirs();
        }
        File jmeterDir = new File(tarProjectPath + "/" + AGTCommonProperties.PROJECT_TEST_ROOT_PATH_PREFIX + "jmeter");
        if (!jmeterDir.exists()) {
            jmeterDir.mkdirs();
        }
    }

    private static InputStream formatPomConfig(InputStream pomInputStream) {
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(pomInputStream, writer);
            String rawStr = writer.toString();
            rawStr = StringUtils.replace(rawStr, "><dependency>", ">\r\n\t\t<dependency>");
            rawStr = StringUtils.replace(rawStr, "></dependencies>", ">\r\n\t</dependencies>");
            rawStr = StringUtils.replace(rawStr, "></project>", ">\r\n</project>");
            return new ByteArrayInputStream(rawStr.getBytes());
        } catch (Exception e) {
            throw new AGTBizException("创建模板失败", "EXP00008", e);
        } finally {
            IOUtils.closeQuietly(pomInputStream);
            IOUtils.closeQuietly(writer);
        }
    }

    private static InputStream mergePomConfig(InputStream srcInputStream, File tarFile) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document tarDocument = builder.parse(tarFile);
            Document srcDocument = builder.parse(srcInputStream);
            NodeList srcDependencyList = srcDocument.getElementsByTagName("dependency");
            NodeList srcDependenciesList = srcDocument.getElementsByTagName("dependencies");
            NodeList tarDependenciesList = tarDocument.getElementsByTagName("dependencies");
            
            Node tarNode = null;
            if (tarDependenciesList != null && tarDependenciesList.getLength() > 0) {
	            for (int i = 0; i < tarDependenciesList.getLength(); i++) {
	            	Node dependenciesNode = tarDependenciesList.item(i);
	            	
	            	if (!dependenciesNode.getParentNode().getNodeName().equals("dependencyManagement")) {
	            		tarNode = dependenciesNode;
	            		break;
	            	}
	            }
            }
            
            NodeList tarProjectList = tarDocument.getElementsByTagName("project");
            if (tarNode != null) {
                for (int i = 0; i < srcDependencyList.getLength(); i++) {
                    Node node = srcDependencyList.item(i);
                    node = tarDocument.importNode(node, true);
                    
                    if (checkDependencyExists(node, tarNode.getChildNodes())) {
                        continue;
                    }
                    tarNode.appendChild(node);
                }
            } else if (tarProjectList != null && tarProjectList.getLength() > 0) {
                tarNode = tarProjectList.item(0);
                Node node = srcDependenciesList.item(0);
                node = tarDocument.importNode(node, true);
                tarNode.appendChild(node);
            }
            TransformerFactory transformerFactory  =  TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.transform(new DOMSource(tarDocument), new StreamResult(bos));
            return new ByteArrayInputStream(bos.toByteArray());
        } catch (Exception e) {
            throw new AGTBizException("创建模板失败", "EXP00008", e);
        } finally {
            IOUtils.closeQuietly(srcInputStream);
            IOUtils.closeQuietly(bos);
        }
    }
    
    private static boolean checkDependencyExists(Node checkNode, NodeList nodeList) {
        NodeList checkList = checkNode.getChildNodes();
        String groupId = null;
        String artifactId = null;

        boolean hasGroup = false;
        boolean hasArtifact = false;
        if (checkList != null && checkList.getLength() > 0) {
            for (int i = 0; i < checkList.getLength(); i++) {
                Node node = checkList.item(i);
                System.out.println("!---" + node.getTextContent());
                if (node != null && StringUtils.equals("groupId", node.getNodeName())) {
                    groupId = node.getTextContent();
                }
                if (node != null && StringUtils.equals("artifactId", node.getNodeName())) {
                    artifactId = node.getTextContent();
                }
            }
        }
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node childNode = nodeList.item(i);
                if (!StringUtils.equals(checkNode.getNodeName(), childNode.getNodeName())) {
                    continue;
                }
                NodeList attrList = childNode.getChildNodes();
                if (attrList != null && attrList.getLength() > 0) {
                    for (int j = 0; j < attrList.getLength(); j++) {
                        Node attrNode = attrList.item(j);
                        System.out.println("?---" + attrNode.getTextContent());
                        if (StringUtils.equals(groupId, attrNode.getTextContent())) {
                            hasGroup = true;
                        }
                        if (StringUtils.equals(artifactId, attrNode.getTextContent())) {
                            hasArtifact = true;
                        }
                        if (hasGroup && hasArtifact) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private static InputStream replacePomConfig(InputStream srcInputStream, File tarFile) {
        StringWriter srcWriter = new StringWriter();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(tarFile);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath path = xpathFactory.newXPath();
            String groupId = (String)path.evaluate("/project/groupId", document, XPathConstants.STRING);
            String artifactId = (String)path.evaluate("/project/artifactId", document, XPathConstants.STRING);
            String version = (String)path.evaluate("/project/version", document, XPathConstants.STRING);

            IOUtils.copy(srcInputStream, srcWriter);
            String srcContent = srcWriter.toString();

            srcContent = StringUtils.replace(srcContent, "${groupId}", groupId);
            srcContent = StringUtils.replace(srcContent, "${artifactId}", artifactId);
            srcContent = StringUtils.replace(srcContent, "${version}", version);

            return new ByteArrayInputStream(srcContent.getBytes());
        } catch (Exception e) {
            throw new AGTBizException("创建模板失败", "EXP00008", e);
        } finally {
            IOUtils.closeQuietly(srcInputStream);
            IOUtils.closeQuietly(srcWriter);
        }
    }

    private static void createTestFile(String tarProjectPath, String fileList) {
        String[] rawList = StringUtils.split(fileList, ",");
        for (String tarName : rawList) {
            tarName = StringUtils.strip(tarName);
            InputStream inputStream = TestBaseGenerateProcessor.class.getClassLoader().getResourceAsStream(AGTCommonProperties.PROJECT_TEST_CONFIGURE_PREFIX + tarName);
            String tarPath = tarProjectPath + "/" + MessageConfigHandler.findMappingResult(tarName);
            File tarDir = new File(StringUtils.substringBeforeLast(tarPath, "/"));
            if (!tarDir.exists()) {
                boolean flag = tarDir.mkdirs();
                if (!flag) {
                    throw new AGTBizException("创建模板失败", "EXP00008");
                }
            }
            File tarFile = new File(tarPath);
            if (tarFile.exists()) {
                backUpTargetFile(tarFile);
            }
            copyBaseTestFile(inputStream, tarFile);
        }
    }

    private static void backUpTargetFile(File tarFile) {
        File bakFile = new File(tarFile.getAbsolutePath().concat(".bak"));
        copyBaseTestFile(tarFile, bakFile);
    }

    private static void copyBaseTestFile(InputStream is, File targetFile) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(targetFile);
            IOUtils.copy(is, os);
        } catch (Exception e) {
            throw new AGTBizException("创建模板失败", "EXP00008", e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

    private static void copyBaseTestFile(File sourceFile, File targetFile) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(sourceFile);
            os = new FileOutputStream(targetFile);
            IOUtils.copy(is, os);
        } catch (Exception e) {
            throw new AGTBizException("创建模板失败", "EXP00008", e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }
}
