import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class XMLParser {

    public static void main(String[] args) {

        try {
            File inputFile = new File("/Users/Ollie/IdeaProjects/unlockyork/src/main/resources/example.pws");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Slide");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                System.out.println("list size : " +nList.getLength());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element slide = (Element) nNode;
                    System.out.println("Slide duration : "
                            + slide.getAttribute("duration"));

                    System.out.println("text : "
                            + slide
                            .getElementsByTagName("Text")
                            .item(0)
                            .getTextContent());

                    System.out.println("text attributes : "
                            + slide.getElementsByTagName("Text")
                            .item(0).getAttributes().getNamedItem("textsize"));

                    System.out.println("text2 : "
                            + slide
                            .getElementsByTagName("Text")
                            .item(1)
                            .getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
