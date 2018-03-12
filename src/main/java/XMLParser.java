import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import models.*;
import models.Colours;
import models.Fonts;
import models.Position;
import models.Slide;
import models.Text;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import org.xml.sax.SAXException;


public class XMLParser {

    public static void main(String[] args) {

        //Slide slides = new Slide();

        //Presentation pres = new Presentation();

        // parse an XML document into a DOM tree
        DocumentBuilder parser = null;
        try {
            parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            if (parser != null)
                document = parser.parse(new File("/Users/jonathantrain/IdeaProjects/unlockyork/src/main/resources/example.pws"));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File("/Users/jonathantrain/IdeaProjects/unlockyork/src/main/resources/schema.xsd"));
        Schema schema = null;
        try {
            schema = factory.newSchema(schemaFile);
        } catch (SAXException e) {
            e.printStackTrace();
        }

        // create a Validator instance, which can be used to validate an instance document
        Validator validator = null;
        if (schema != null) {
            validator = schema.newValidator();
        }

        // validate the DOM tree
        try {
            try {
                if (validator != null) {
                    validator.validate(new DOMSource(document));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SAXException e) {
            // instance document is invalid!
        }

        try {
            //File inputFile = new File("/Users/jonathantrain/IdeaProjects/unlockyork/src/main/resources/example.pws");
            //DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            //DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            //Document doc = dBuilder.parse(inputFile);
           // doc.getDocumentElement().normalize();
           // System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList slideList = document.getElementsByTagName("Slide");
            NodeList slideElements;
            Node slide;
            NodeList presentation = document.getElementsByTagName("Presentation");



            Element deflauts = document.getDocumentElement();

            //slideList.item(0).getParentNode().
            //Element meta = document.getDocumentElement();
            //System.out.println("defaults: " + document.getDocumentElement().getAttribute("font"));




            System.out.println("----------------------------");

            //getMeta(presentation.item(0).getFirstChild());

            //System.out.println("meta: " + slideList.item(0).getNodeName());

            for (int i = 0; i < slideList.getLength(); i++) {
                slide = slideList.item(i);

                //slide.getChildNodes();




                System.out.println("\n/////////////////// SLIDE: " + i + " ///////////////////");
                System.out.println("\nCurrent Element :" + slide.getNodeName() + " SLIDE : " + i);
                System.out.println("list size : " +slideList.getLength());

                if (slide.getNodeType() == Node.ELEMENT_NODE) {

                    if(slide.hasAttributes() == true)
                    {
                        System.out.println("slide duration: " + slide.getAttributes().getNamedItem("duration"));
                        System.out.println("slide fill: " + slide.getAttributes().getNamedItem("fill"));
                        System.out.println("slide colour: " + slide.getAttributes().getNamedItem("color"));
                        System.out.println("slide font: " + slide.getAttributes().getNamedItem("font"));

                    }

                    System.out.println("\n-----------SLIDE - ELEMENTS----------");

                    if(slide.hasChildNodes() == true)
                    {
                        slideElements = slide.getChildNodes();

                        //equals("gfhd")

                        for(int n = 0; n < slideElements.getLength(); n++)
                        {
                            System.out.println(slideElements.item(n).getNodeName());

                            if(slideElements.item(n).getNodeName().equals("Text")) {
                                if (slideElements.item(n).hasAttributes()) {
                                    System.out.println("   size: " + slideElements.item(n).getAttributes().getNamedItem("textsize"));
                                    System.out.println("   underline: " + slideElements.item(n).getAttributes().getNamedItem("underline"));
                                    System.out.println("   bold: " + slideElements.item(n).getAttributes().getNamedItem("bold"));
                                    System.out.println("   italic: " + slideElements.item(n).getAttributes().getNamedItem("italic"));
                                    System.out.println("   font: " + slideElements.item(n).getAttributes().getNamedItem("font"));

                                }

                                System.out.println("text content: " + slideElements.item(n).getTextContent());


                                if (slideElements.item(n).hasChildNodes() == true) {
                                    System.out.println(slideElements.item(n).getChildNodes().item(0));
                                    for(int x = 0; x < slideElements.item(n).getChildNodes().getLength(); x++)
                                    {
                                        if(slideElements.item(n).getChildNodes().item(x).getNodeName().equals("Format")) {
                                            System.out.println("    " + slideElements.item(n).getChildNodes().item(x).getNodeName());
                                            if (slideElements.item(n).getChildNodes().item(x).hasAttributes()) {
                                                System.out.println("        size: " + slideElements.item(n).getChildNodes().item(x).getAttributes().getNamedItem("textsize"));
                                                System.out.println("        underline: " + slideElements.item(n).getChildNodes().item(x).getAttributes().getNamedItem("underline"));
                                                System.out.println("        bold: " + slideElements.item(n).getChildNodes().item(x).getAttributes().getNamedItem("bold"));
                                                System.out.println("        italic: " + slideElements.item(n).getChildNodes().item(x).getAttributes().getNamedItem("italic"));
                                                System.out.println("        font: " + slideElements.item(n).getChildNodes().item(x).getAttributes().getNamedItem("font"));
                                                System.out.println("        colour: " + slideElements.item(n).getChildNodes().item(x).getAttributes().getNamedItem("color"));

                                            }
                                            System.out.println("        Format content: " + slideElements.item(n).getChildNodes().item(x).getTextContent());
                                        }
                                    }



                                }
                            }


                            if(slideElements.item(n).getNodeName().equals("Shape"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    XMLParser.getSlideShape(slideElements.item(n), deflauts);

                                }
                            }

                            if(slideElements.item(n).getNodeName().equals("Image"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    XMLParser.getSlideImage(slideElements.item(n));
                                }
                            }

                            if(slideElements.item(n).getNodeName().equals("Video"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    XMLParser.getSlideVideo(slideElements.item(n));
                                }
                            }

                            if(slideElements.item(n).getNodeName().equals("Audio"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                      XMLParser.getSlideAudio(slideElements.item(n));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getSlideAudio(Node slide)
    {

        Position audioPosition = new Position();

        audioPosition.setxTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("x").getNodeValue()));
        audioPosition.setyTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("y").getNodeValue()));
        audioPosition.setxBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("x2").getNodeValue()));
        audioPosition.setyBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("y2").getNodeValue()));


        Audio slideAudio = new Audio();

        slideAudio.setPath(slide.getAttributes().getNamedItem("path").getNodeValue());
        slideAudio.setPosition(audioPosition);


        System.out.println("slideAudio");
        System.out.println("    x: " + slideAudio.getPosition().getxTopLeft());
        System.out.println("    y: " + slideAudio.getPosition().getyTopLeft());
        System.out.println("    x2: " + slideAudio.getPosition().getxBottomRight());
        System.out.println("    x2: " + slideAudio.getPosition().getyBottomRight());
        System.out.println("    path: " + slideAudio.getPath());

        //return slideAudio;

    }

    public static void getSlideVideo(Node slide)
    {
        Position videoPosition = new Position();

        videoPosition.setxTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("x").getNodeValue()));
        videoPosition.setyTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("y").getNodeValue()));
        videoPosition.setxBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("x2").getNodeValue()));
        videoPosition.setyBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("y2").getNodeValue()));

        Video slideVideo = new Video();

        slideVideo.setPath(slide.getAttributes().getNamedItem("path").getNodeValue());
        slideVideo.setPosition(videoPosition);

        System.out.println("slideAudio");
        System.out.println("    x: " + slideVideo.getPosition().getxTopLeft());
        System.out.println("    y: " + slideVideo.getPosition().getyTopLeft());
        System.out.println("    x2: " + slideVideo.getPosition().getxBottomRight());
        System.out.println("    x2: " + slideVideo.getPosition().getyBottomRight());
        System.out.println("    path: " + slideVideo.getPath());
    }

    public static void getSlideShape(Node slide, Element defaults)
    {
        Position shapePosition = new Position();

        shapePosition.setxTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("x").getNodeValue()));
        shapePosition.setyTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("y").getNodeValue()));
        shapePosition.setxBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("x2").getNodeValue()));
        shapePosition.setyBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("y2").getNodeValue()));

        Colours shapeColour = new Colours();

        if(slide.getAttributes().getNamedItem("color") != null)
        {
            shapeColour.setColour(slide.getAttributes().getNamedItem("color").getNodeValue());
        }
        else
        {
           // shapeColour.setColour(defaults.getAttributes().getNamedItem("colour").getNodeValue());
            System.out.println("de colour shape: " + defaults.getAttributes().getNamedItem("color").getNodeValue());
        }

        if(slide.getAttributes().getNamedItem("fill") != null)
        {
            shapeColour.setFill(slide.getAttributes().getNamedItem("fill").getNodeValue());
        }
        else
        {
            shapeColour.setFill(defaults.getAttributes().getNamedItem("fill").getNodeValue());
        }

        Shape slideShape = new Shape();

        slideShape.setShape(slide.getAttributes().getNamedItem("type").getNodeValue());

        if(slide.getAttributes().getNamedItem("stroke") != null)
        {
            slideShape.setStroke(Integer.parseInt(slide.getAttributes().getNamedItem("stroke").getNodeValue()));
        }

        slideShape.setColour(shapeColour);
        slideShape.setPosition(shapePosition);


        System.out.println("   x: " + slideShape.getPosition().getxTopLeft());
        System.out.println("   y: " + slideShape.getPosition().getyTopLeft());
        System.out.println("   type: " + slideShape.getShape());
        System.out.println("   x2: " + slideShape.getPosition().getxBottomRight());
        System.out.println("   y2: " + slideShape.getPosition().getyBottomRight());
        System.out.println("   stroke: " + slideShape.getStroke());
        System.out.println("   colour: " + slideShape.getColour().getColour());
        System.out.println("   fill: " + slideShape.getColour().getFill());

    }

    public static void getSlideImage(Node slide)
    {
        Position imagePosition = new Position();

        imagePosition.setxTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("x").getNodeValue()));
        imagePosition.setyTopLeft(Integer.parseInt(slide.getAttributes().getNamedItem("y").getNodeValue()));
        imagePosition.setxBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("x2").getNodeValue()));
        imagePosition.setyBottomRight(Integer.parseInt(slide.getAttributes().getNamedItem("y2").getNodeValue()));

        Image slideImage = new Image();

        slideImage.setPath(slide.getAttributes().getNamedItem("path").getNodeValue());
        slideImage.setPosition(imagePosition);

        System.out.println("   x: " + slideImage.getPosition().getxTopLeft());
        System.out.println("   y: " + slideImage.getPosition().getyTopLeft());
        System.out.println("   x2: " + slideImage.getPosition().getxBottomRight());
        System.out.println("   y2: " + slideImage.getPosition().getyBottomRight());
        System.out.println("   path: " + slideImage.getPath());

    }

    public static void getMeta(Node presMeta)
    {
        Meta meta = new Meta();

        meta.setKey(presMeta.getAttributes().getNamedItem("key").getNodeValue());
        meta.setValue(presMeta.getAttributes().getNamedItem("value").getNodeValue());

        System.out.println("META key: " + meta.getKey() + "author: " + meta.getValue());

    }


}
