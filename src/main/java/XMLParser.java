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

import static java.lang.Boolean.parseBoolean;


public class XMLParser {

    public static void main(String[] args) {

        Presentation presentation = new Presentation();

        XMLParser parser = new XMLParser();
        presentation = parser.parser("src/build/resources/main/example.pws", "src/build/resources/main/schema.xsd");
        System.out.println("finished");
    }

    public Presentation parser(String xmlPath, String schemaPath)
    {
        //creates a blank presentation object that will contain the parsed presentation
        Presentation presentation = new Presentation();

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
                document = parser.parse(new File(xmlPath)); //File("src/build/resources/main/example.pws")
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        // create a SchemaFactory capable of understanding WXS schemas
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

        // load a WXS schema, represented by a Schema instance
        Source schemaFile = new StreamSource(new File(schemaPath)); //File("src/build/resources/main/schema.xsd")
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
            NodeList slideList = null;
            if (document != null) {
                slideList = document.getElementsByTagName("Slide");
            }

            NodeList slideElements;

            Node xmlSlide;

            Element deflauts = document.getDocumentElement();

            NamedNodeMap presDeflauts =  deflauts.getAttributes();

            //sets the presentation defaults for font and colour
            getPresentationDeflaults(presentation, presDeflauts);

            //get the meta from the xml
            if(deflauts.getElementsByTagName("Meta").item(0) != null)
            {
                presentation.setMeta(getMeta(deflauts.getElementsByTagName("Meta").item(0).getAttributes()));
            }


            //get the gps from the xml
            if(deflauts.getElementsByTagName("GPS").item(0) != null)
            {
                presentation.setGps(getGps(deflauts.getElementsByTagName("GPS").item(0).getAttributes()));
            }

            System.out.println("----------------------------");

            // loop through all slide elements
            for (int i = 0; i < slideList.getLength(); i++) {

                //get the specified slide element from xml
                xmlSlide = slideList.item(i);

                Slide slide = new Slide();

                // sets the default values found in the presentation
                setSlideDefaults(slide, presentation);


                System.out.println("\n/////////////////// SLIDE: " + i + " ///////////////////");
                System.out.println("\nCurrent Element :" + xmlSlide.getNodeName() + " SLIDE : " + i);
                System.out.println("list size : " +slideList.getLength());

                //if node is actually an element
                if (xmlSlide.getNodeType() == Node.ELEMENT_NODE) {

                    // pulls the slide attributes if it has any and will overwrite the default values from presentation
                    getSlideDefaults(slide, xmlSlide.getAttributes());

                    System.out.println("\n-----------------------------SLIDE - ELEMENTS-------------------------------------");

                    // if the slide actually as elements to it
                    if(xmlSlide.hasChildNodes())
                    {
                        // saves all slide elements into slideElements
                        slideElements = xmlSlide.getChildNodes();

                        //loops through all elements found for each slide
                        for(int n = 0; n < slideElements.getLength(); n++)
                        {
                            System.out.println(slideElements.item(n).getNodeName());

                            // if the element is text, will pull that element from the xml
                            if(slideElements.item(n).getNodeName().equals("Text")) {

                                System.out.println("\n-----------text----------");

                                //adds text object to the slide
                                slide.getText().add(getText(slideElements.item(n), slide));

                                System.out.println("\n");

                            }

                            // if the element is shape, will pull that element from the xml
                            if(slideElements.item(n).getNodeName().equals("Shape"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    System.out.println("\n-----------shape----------");
                                    //adds shape object to the slide
                                    slide.getShape().add(getSlideShape(slideElements.item(n), slide));

                                    System.out.println("\n");
                                }
                            }

                            // if the element is image, will pull that element from the xml
                            if(slideElements.item(n).getNodeName().equals("Image"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    System.out.println("\n-----------image----------");

                                    //adds image object to the slide
                                    slide.getImage().add(getSlideImage(slideElements.item(n)));

                                    System.out.println("\n");
                                }
                            }

                            // if the element is video, will pull that element from the xml
                            if(slideElements.item(n).getNodeName().equals("Video"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    System.out.println("\n-----------video----------");

                                    //adds video object to the slide
                                    slide.getVideo().add(getSlideVideo(slideElements.item(n)));

                                    System.out.println("\n");
                                }
                            }

                            // if the element is audio, will pull that element from the xml
                            if(slideElements.item(n).getNodeName().equals("Audio"))
                            {
                                if(slideElements.item(n).hasAttributes())
                                {
                                    System.out.println("\n-----------audio----------");

                                    //adds audio object to the slide
                                    slide.getAudio().add(getSlideAudio(slideElements.item(n)));

                                    System.out.println("\n");
                                }
                            }
                        }
                    }
                }

                // adds slide to the slide array in presentation
                presentation.getSlides().add(slide);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return presentation;
    }

    //pulls all audio information from the dom data structure and saves it into an audio object
    public static Audio getSlideAudio(Node xmlSlide)
    {

        Position audioPosition = new Position();

        audioPosition.setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        audioPosition.setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        audioPosition.setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        audioPosition.setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        Audio slideAudio = new Audio();

        slideAudio.setPath(xmlSlide.getAttributes().getNamedItem("path").getNodeValue());
        slideAudio.setPosition(audioPosition);

        System.out.println("slideAudio");
        System.out.println("    x: " + slideAudio.getPosition().getxTopLeft());
        System.out.println("    y: " + slideAudio.getPosition().getyTopLeft());
        System.out.println("    x2: " + slideAudio.getPosition().getxBottomRight());
        System.out.println("    x2: " + slideAudio.getPosition().getyBottomRight());
        System.out.println("    path: " + slideAudio.getPath());

        return slideAudio;

    }

    //pulls all video information from the dom data structure and saves it into an video object
    public static Video getSlideVideo(Node xmlSlide)
    {
        Position videoPosition = new Position();

        videoPosition.setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        videoPosition.setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        videoPosition.setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        videoPosition.setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        Video slideVideo = new Video();

        slideVideo.setPath(xmlSlide.getAttributes().getNamedItem("path").getNodeValue());
        slideVideo.setPosition(videoPosition);

        System.out.println("slideAudio");
        System.out.println("    x: " + slideVideo.getPosition().getxTopLeft());
        System.out.println("    y: " + slideVideo.getPosition().getyTopLeft());
        System.out.println("    x2: " + slideVideo.getPosition().getxBottomRight());
        System.out.println("    x2: " + slideVideo.getPosition().getyBottomRight());
        System.out.println("    path: " + slideVideo.getPath());

        return slideVideo;
    }

    //pulls all shape information from the dom data structure and saves it into an shape object
    public static Shape getSlideShape(Node xmlSlide, Slide slide)
    {

        Shape slideShape = new Shape();

        slideShape.getPosition().setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        slideShape.getPosition().setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        slideShape.getPosition().setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        slideShape.getPosition().setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        // sets default values for colour from the slide/presentation defaults
        slideShape.getColour().setFill(slide.getColour().getFill());
        slideShape.getColour().setColour(slide.getColour().getColour());

        // if the colour has been specified in the xml, it will overwrite the defaults
        if(xmlSlide.getAttributes().getNamedItem("color") != null)
        {
            slideShape.getColour().setColour(xmlSlide.getAttributes().getNamedItem("color").getNodeValue());
        }

        // if the colour fill has been specified in the xml, it will overwrite the defaults
        if(xmlSlide.getAttributes().getNamedItem("fill") != null)
        {
            slideShape.getColour().setFill(xmlSlide.getAttributes().getNamedItem("fill").getNodeValue());
        }

        //gets the type of shape "triangle", "square" etc.
        slideShape.setShape(xmlSlide.getAttributes().getNamedItem("type").getNodeValue());

        //if a stroke value has been specified then it will be added to the shape object
        if(xmlSlide.getAttributes().getNamedItem("stroke") != null)
        {
            slideShape.setStroke(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("stroke").getNodeValue()));
        }

        System.out.println("   x: " + slideShape.getPosition().getxTopLeft());
        System.out.println("   y: " + slideShape.getPosition().getyTopLeft());
        System.out.println("   type: " + slideShape.getShape());
        System.out.println("   x2: " + slideShape.getPosition().getxBottomRight());
        System.out.println("   y2: " + slideShape.getPosition().getyBottomRight());
        System.out.println("   stroke: " + slideShape.getStroke());
        System.out.println("   colour: " + slideShape.getColour().getColour());
        System.out.println("   fill: " + slideShape.getColour().getFill());

        return slideShape;
    }

    //pulls all image information from the dom data structure and saves it into an image object
    public static Image getSlideImage(Node xmlSlide)
    {
        Position imagePosition = new Position();

        imagePosition.setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        imagePosition.setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        imagePosition.setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        imagePosition.setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        Image slideImage = new Image();

        slideImage.setPath(xmlSlide.getAttributes().getNamedItem("path").getNodeValue());
        slideImage.setPosition(imagePosition);

        System.out.println("   x: " + slideImage.getPosition().getxTopLeft());
        System.out.println("   y: " + slideImage.getPosition().getyTopLeft());
        System.out.println("   x2: " + slideImage.getPosition().getxBottomRight());
        System.out.println("   y2: " + slideImage.getPosition().getyBottomRight());
        System.out.println("   path: " + slideImage.getPath());

        return slideImage;

    }

    // gets the attributes for the text object element in the xml
    public static void getTextAttributes(Text text, NamedNodeMap xmlSlide)
    {
        if(xmlSlide.getNamedItem("duration") != null)
        {
            text.getTransition().setDuration(Integer.parseInt(xmlSlide.getNamedItem("duration").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("start") != null)
        {
            text.getTransition().setStartTrigger(xmlSlide.getNamedItem("start").getNodeValue());
        }

        if(xmlSlide.getNamedItem("x") != null)
        {
            text.getPosition().setxTopLeft(Integer.parseInt(xmlSlide.getNamedItem("x").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("y") != null)
        {
            text.getPosition().setyTopLeft(Integer.parseInt(xmlSlide.getNamedItem("y").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("x2") != null)
        {
            text.getPosition().setxBottomRight(Integer.parseInt(xmlSlide.getNamedItem("x2").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("y2") != null)
        {
            text.getPosition().setyBottomRight(Integer.parseInt(xmlSlide.getNamedItem("y2").getNodeValue()));
        }
    }

    //gets the attributes for the textContent object element in xml
    public static void getTextContentAttributes(textContent content, NamedNodeMap xmlSlide)
    {
        if(xmlSlide.getNamedItem("fill") != null) {
            content.getColour().setFill(xmlSlide.getNamedItem("fill").getNodeValue());
        }

        if(xmlSlide.getNamedItem("color") != null) {
            content.getColour().setColour(xmlSlide.getNamedItem("color").getNodeValue());
        }

        if(xmlSlide.getNamedItem("italic") != null)
        {
            content.getFont().setItalic(parseBoolean(xmlSlide.getNamedItem("italic").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("bold") != null)
        {
            content.getFont().setBold(parseBoolean(xmlSlide.getNamedItem("bold").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("underline") != null)
        {
            content.getFont().setUnderline(parseBoolean(xmlSlide.getNamedItem("underline").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("textsize") != null)
        {
            content.getFont().setTextSize(Integer.parseInt(xmlSlide.getNamedItem("textsize").getNodeValue()));
        }

        if(xmlSlide.getNamedItem("font") != null)
        {
            content.getFont().setFont(xmlSlide.getNamedItem("font").getNodeValue());
        }
    }

    //sets the defaults from the slide/presentation for the text content objects
    //ensures text always has a format
    public static void setTextContentDefaults(textContent content, Slide slide)
    {
        content.getFont().setBold(slide.getFont().isBold());
        content.getFont().setUnderline(slide.getFont().isUnderline());
        content.getFont().setItalic(slide.getFont().isItalic());
        content.getFont().setTextSize(slide.getFont().getTextSize());
        content.getFont().setFont(slide.getFont().getFont());

        content.getColour().setColour(slide.getColour().getColour());
        content.getColour().setFill(slide.getColour().getFill());
    }

    //pulls the actual text from the text elements from xml
    public static Text getText(Node xmlSlide, Slide slide)
    {
        Text slideText = new Text();

        //sets the transition default from the slide
        slideText.setTransition(slide.getTransitions());

        //pulls all the attributes for the Text object when specified in the xml
        getTextAttributes(slideText, xmlSlide.getAttributes());

        //loops through all lines of text written in the xml
        for(int i = 0; i < xmlSlide.getChildNodes().getLength(); i++)
        {
                textContent contentText = new textContent();

                //sets the defaults for a new textContent from slide/presentation defaults
                setTextContentDefaults(contentText, slide);

                System.out.println("SLIDE DE BOLD: " + slide.getFont().isBold());

                //gets the attributes specified from the Text element, these will overwrite the defaults when needed
                getTextContentAttributes(contentText, xmlSlide.getAttributes());

                ///////////////////BOLD/////////////////////////////////////////////////
                //if a format change is specified for bold this particular textContent will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("bold") != null) {
                    contentText.getFont().setBold(parseBoolean(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("bold").getNodeValue()));
                    System.out.println("bold from FORMAT");
                }

                /////////////////////underline//////////////////////////////////////////
                //if a format change is specified for underline this particular textContent will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("underline") != null) {
                    contentText.getFont().setUnderline(parseBoolean(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("underline").getNodeValue()));
                    System.out.println("underline from FORMAT");
                }

                ////////////////////italic//////////////////////////////////////////////
                //if a format change is specified for italic this particular textContent will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("italic") != null) {
                    contentText.getFont().setItalic(parseBoolean(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("italic").getNodeValue()));
                    System.out.println("italic from FORMAT");
                }

                ///////////////////textsize////////////////////////////////////////////
                //if a format change is specified for textsize this particular textContent will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("textsize") != null) {
                    contentText.getFont().setTextSize(Integer.parseInt(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("textsize").getNodeValue()));
                    System.out.println("textsize from FORMAT");
                }

                ///////////////////////font///////////////////////////////////////////
                //if a format change is specified for font this particular textContent will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("font") != null) {
                    contentText.getFont().setFont(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("font").getNodeValue());
                    System.out.println("font from FORMAT");
                }

                //////////////////colour/////////////////////////////////////////////
                //if a format change is specified for colour this particular textContent will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("color") != null) {

                    contentText.getColour().setColour(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("color").getNodeValue());
                    System.out.println("colour from FORMAT");

                }

                ///////////////////////line break///////////////////////////////////
                //when a line break as been specified the content is set to be a line break
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Br")) {
                    System.out.println("---- Br ---");
                    contentText.setContent("\n");
                }
                else
                {
                    //if not a line break, the actual text content of the line is added to the textContent object
                    contentText.setContent(xmlSlide.getChildNodes().item(i).getTextContent().trim());
                }

                if(contentText.getContent() == "\n")
                {
                    System.out.println("LINE BREAK");
                }

                System.out.println("content TEXT , bold: " + contentText.getFont().isBold()
                        + " ,italic: " + contentText.getFont().isItalic()
                        + " ,underline: " + contentText.getFont().isUnderline()
                        + " ,textsize: " + contentText.getFont().getTextSize()
                        + " ,colour: " + contentText.getColour().getColour()
                        + " ,fill: " + contentText.getColour().getFill()
                        + " ,font: " + contentText.getFont().getFont()
                        + " , TEXT: " + contentText.getContent());

                slideText.getContent().add(i, contentText);
            }


        return slideText;
    }

    //gets the meta from the dom structure and creates a meta object
    public static Meta getMeta(NamedNodeMap xmlMeta)
    {
        Meta slideMeta = new Meta();

        slideMeta.setValue(xmlMeta.getNamedItem("value").getNodeValue());
        slideMeta.setKey(xmlMeta.getNamedItem("key").getNodeValue());

        System.out.println("META - value: " + slideMeta.getValue() + " - key: " + slideMeta.getKey());

        return slideMeta;
    }

    //gets the presentation object defaults from the presentation attributes
    public static void getPresentationDeflaults(Presentation presentation, NamedNodeMap xmlDefaults)
    {
        if(xmlDefaults.getNamedItem("italic") != null)
        {
            presentation.getPresDefaultFont().setItalic(parseBoolean(xmlDefaults.getNamedItem("italic").getNodeValue()));
            System.out.println("PRES italic: " + presentation.getPresDefaultFont().isItalic());
        }

        if(xmlDefaults.getNamedItem("bold") != null)
        {
            presentation.getPresDefaultFont().setBold(parseBoolean(xmlDefaults.getNamedItem("bold").getNodeValue()));
            System.out.println("PRES bold: " + presentation.getPresDefaultFont().isBold());
        }

        if(xmlDefaults.getNamedItem("underline") != null)
        {
            presentation.getPresDefaultFont().setUnderline(parseBoolean(xmlDefaults.getNamedItem("underline").getNodeValue()));
            System.out.println("PRES underline: " + presentation.getPresDefaultFont().isUnderline());
        }

        if(xmlDefaults.getNamedItem("textsize") != null)
        {
            presentation.getPresDefaultFont().setTextSize(Integer.parseInt(xmlDefaults.getNamedItem("textsize").getNodeValue()));
            System.out.println("PRES textsize: " + presentation.getPresDefaultFont().getTextSize());
        }

        if(xmlDefaults.getNamedItem("color") != null)
        {
            presentation.getPresDefaultColour().setColour(xmlDefaults.getNamedItem("color").getNodeValue());
            System.out.println("PRES colour: " + presentation.getPresDefaultColour().getColour());
        }

        if(xmlDefaults.getNamedItem("font") != null)
        {
            presentation.getPresDefaultFont().setFont(xmlDefaults.getNamedItem("font").getNodeValue());
            System.out.println("PRES font: " + presentation.getPresDefaultFont().getFont());
        }

        if(xmlDefaults.getNamedItem("fill") != null)
        {
            presentation.getPresDefaultColour().setFill(xmlDefaults.getNamedItem("fill").getNodeValue());
            System.out.println("PRES fill: " + presentation.getPresDefaultColour().getFill());
        }
    }

    //gets the slide defaults from the slide attributes in the dom structure
    public static void getSlideDefaults(Slide slide, NamedNodeMap xmlSlide)
    {
        if(xmlSlide.getNamedItem("duration") != null) {
            slide.getTransitions().setDuration(Integer.parseInt(xmlSlide.getNamedItem("duration").getNodeValue()));
            System.out.println("SLIDE duration: " + slide.getTransitions().getDuration());
        }

        if(xmlSlide.getNamedItem("start") != null) {
            slide.getTransitions().setStartTrigger(xmlSlide.getNamedItem("start").getNodeValue());
            System.out.println("SLIDE start: " + slide.getTransitions().getStartTrigger());
        }

        if(xmlSlide.getNamedItem("fill") != null) {
            slide.getColour().setFill(xmlSlide.getNamedItem("fill").getNodeValue());
            System.out.println("SLIDE fill: " + slide.getColour().getFill());
        }

        if(xmlSlide.getNamedItem("color") != null) {
            slide.getColour().setColour(xmlSlide.getNamedItem("color").getNodeValue());
            System.out.println("SLIDE colour: " + slide.getColour().getColour());
        }

        if(xmlSlide.getNamedItem("italic") != null)
        {
            slide.getFont().setItalic(parseBoolean(xmlSlide.getNamedItem("italic").getNodeValue()));
            System.out.println("SLIDE italic: " + slide.getFont().isItalic());
        }

        if(xmlSlide.getNamedItem("bold") != null)
        {
            slide.getFont().setBold(parseBoolean(xmlSlide.getNamedItem("bold").getNodeValue()));
            System.out.println("SLIDE bold: " + slide.getFont().isBold());
        }

        if(xmlSlide.getNamedItem("underline") != null)
        {
            slide.getFont().setUnderline(parseBoolean(xmlSlide.getNamedItem("underline").getNodeValue()));
            System.out.println("SLIDE underline: " + slide.getFont().isUnderline());
        }

        if(xmlSlide.getNamedItem("textsize") != null)
        {
            slide.getFont().setTextSize(Integer.parseInt(xmlSlide.getNamedItem("textsize").getNodeValue()));
            System.out.println("SLIDE textsize: " + slide.getFont().getTextSize());
        }

        if(xmlSlide.getNamedItem("font") != null)
        {
            slide.getFont().setFont(xmlSlide.getNamedItem("font").getNodeValue());
            System.out.println("PRES font: " + slide.getFont().getFont());
        }
    }

    //sets the slide defaults from the presentation defaults
    public static void setSlideDefaults(Slide slide, Presentation presentation)
    {
        slide.getFont().setFont(presentation.getPresDefaultFont().getFont());
        slide.getFont().setTextSize(presentation.getPresDefaultFont().getTextSize());
        slide.getFont().setItalic(presentation.getPresDefaultFont().isItalic());
        slide.getFont().setBold(presentation.getPresDefaultFont().isBold());
        slide.getFont().setUnderline(presentation.getPresDefaultFont().isUnderline());

        slide.getColour().setColour(presentation.getPresDefaultColour().getColour());
        slide.getColour().setFill(presentation.getPresDefaultColour().getFill());
    }

    //gets the gps from the dom structure and creates a GPS object and saves it in there
    public static GPS getGps(NamedNodeMap xmlGps)
    {
        GPS slideGps = new GPS();

        slideGps.setElevation(Double.parseDouble(xmlGps.getNamedItem("elevation").getNodeValue()));
        slideGps.setLatitude(Double.parseDouble(xmlGps.getNamedItem("latitude").getNodeValue()));
        slideGps.setLongitude(Double.parseDouble(xmlGps.getNamedItem("longitude").getNodeValue()));

        System.out.println("GPS - elev: " + slideGps.getElevation() + " - lat: " + slideGps.getLatitude() + " - long" + slideGps.getLongitude());

        return slideGps;
    }


}
