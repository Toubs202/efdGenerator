import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.InputSource;
import java.util.ArrayList;

public class File_Reader {
    File file;
    StringBuilder xmlContent;

    public File_Reader(String filepath) {
        file = new File(filepath);
        xmlContent = new StringBuilder();
    }

    public ArrayList<String[]> mapFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String st;
            boolean insideXml = false;
            boolean xmlFinished = false;

            while ((st = br.readLine()) != null) {
                if (!xmlFinished && (st.trim().startsWith("<fl:FlightPlanMessage") || st.trim().startsWith("<fl:FlightDataMessage"))) {
                    insideXml = true;
                }
                if (insideXml) {
                    xmlContent.append(st);
                }
                if (st.trim().endsWith("</fl:FlightPlanMessage>") || st.trim().endsWith("</fl:FlightDataMessage>")) {
                    insideXml = false;
                    xmlFinished = true;
                }
            }

            String xmlString = xmlContent.toString();
            if (xmlString.isEmpty()) {
                System.out.println("No XML data found.");
                return null;
            }

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlString)));

                Element root = document.getDocumentElement();
                ArrayList<String[]> dataList = new ArrayList<>(); // List for key-value pairs

                parseElement(root, "", dataList);

                return dataList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public ArrayList<String[]> mapXML(String tcpString) {
        try (BufferedReader br = new BufferedReader(new StringReader(tcpString))) {
            String st;
            boolean insideXml = false;
            boolean xmlFinished = false;

            while ((st = br.readLine()) != null) {
                if (!xmlFinished && (st.trim().startsWith("<fl:FlightPlanMessage") || st.trim().startsWith("<fl:FlightDataMessage"))) {
                    insideXml = true;
                }
                if (insideXml) {
                    xmlContent.append(st);
                }
                if (st.trim().endsWith("</fl:FlightPlanMessage>") || st.trim().endsWith("</fl:FlightDataMessage>")) {
                    insideXml = false;
                    xmlFinished = true;
                }
            }

            String xmlString = xmlContent.toString();
            if (xmlString.isEmpty()) {
                System.out.println("No XML data found.");
                return null;
            }

            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xmlString)));

                Element root = document.getDocumentElement();
                ArrayList<String[]> dataList = new ArrayList<>(); // List for key-value pairs

                parseElement(root, "", dataList);

                return dataList;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void parseElement(Element element, String parentKey, ArrayList<String[]> dataList) {
        NodeList children = element.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);

            if (child instanceof Element) {
                Element childElement = (Element) child;

                String newKey = parentKey.isEmpty() ? childElement.getTagName() : parentKey + "." + childElement.getTagName();

                if (childElement.getChildNodes().getLength() > 1) {
                    parseElement(childElement, newKey, dataList);
                } else {
                    String[] keyValue = {newKey, childElement.getTextContent()};
                    dataList.add(keyValue); // Adding key-value pair to the list
                }
            }
        }
    }
}
