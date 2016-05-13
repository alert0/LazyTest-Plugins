package lazy.test.plugin.fundation.runner;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import lazy.test.plugin.fundation.common.AGTCommonProperties;
import lazy.test.plugin.fundation.exception.AGTBizException;
import lazy.test.plugin.fundation.model.AGTApplicationContext;
import lazy.test.plugin.fundation.model.TestCaseDataModel;

/**
 * Created by sushidong on 2016/2/26.
 */
public class AutoGenJsfConfigRunner extends AGTRunner {

    public AutoGenJsfConfigRunner(TestCaseDataModel model) {
        super(model);
    }

    @Override
    public void init(AGTApplicationContext context) {
    	
    }

    @Override
    public void run() {
        String className = model.getTestFullClassName();
        String classSimpleName = model.getTestClassName();
        String jsfRegistryId = StringUtils.uncapitalize(classSimpleName);

        try {
            String xmlFileName = model.getGenProjectPath() + "/" + AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX + "jsf-registry.xml";
            String propFileName = model.getGenProjectPath() + "/" + AGTCommonProperties.PROJECT_TEST_RES_PATH_PREFIX + "conf/jsf.properties";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(xmlFileName));
            NodeList nodeList = document.getLastChild().getChildNodes();

            boolean isJsfInjected = false;
            for(int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if(node.getAttributes() != null
                        && node.getAttributes().getNamedItem("id") != null
                        && node.getAttributes().getNamedItem("id").getNodeValue().equals(jsfRegistryId)) {
                    isJsfInjected = true;
                }
            }

            if (!isJsfInjected) {
                Element jsfRegistryElement = document.createElement("jsf:consumer");
                jsfRegistryElement.setAttribute("id", jsfRegistryId);
                jsfRegistryElement.setAttribute("interface", className);
                jsfRegistryElement.setAttribute("protocol", "${jsf." + jsfRegistryId + ".protocol}");
                jsfRegistryElement.setAttribute("alias", "${jsf." + jsfRegistryId + ".alias}");
                jsfRegistryElement.setAttribute("timeout", "${jsf." + jsfRegistryId + ".timeout}");
                jsfRegistryElement.setAttribute("retries", "${jsf." + jsfRegistryId + ".retries}");

                document.getLastChild().appendChild(document.createTextNode("    "));
                document.getLastChild().appendChild(jsfRegistryElement);

                writeXMl(document, xmlFileName);
                writeProperties(jsfRegistryId, propFileName);
            }
        } catch (Exception e) {
            throw new AGTBizException("JSF接口注入失败", "EXP00004");
        }
    }

    private void writeProperties(String jsfRegistryId, String fileName) throws Exception {
        File jsfPropsFile = new File(fileName);
        OutputStreamWriter outputStreamWriter;
        outputStreamWriter = new OutputStreamWriter(new FileOutputStream(jsfPropsFile, true));
        outputStreamWriter.append("\r\n");
        outputStreamWriter.append("\r\njsf." + jsfRegistryId + ".protocol=jsf");
        outputStreamWriter.append("\r\njsf." + jsfRegistryId + ".alias=idn." + jsfRegistryId + ".test");
        outputStreamWriter.append("\r\njsf." + jsfRegistryId + ".timeout=10000");
        outputStreamWriter.append("\r\njsf." + jsfRegistryId + ".retries=0");
        outputStreamWriter.flush();
        outputStreamWriter.close();
    }

    private void writeXMl(Document document, String fileName) throws Exception {
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transFormer = transFactory.newTransformer();
        transFormer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource domSource = new DOMSource(document);

        File file = new File(fileName);
        FileOutputStream out = new FileOutputStream(file);
        StreamResult xmlResult = new StreamResult(out);
        transFormer.transform(domSource, xmlResult);
        out.flush();
        out.close();
    }
}
