import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import models.Text;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import static java.lang.Boolean.parseBoolean;

public class XMLParser {

    public static class ValidationException extends Exception {}

    public static Presentation parse(String xmlPath, String schemaPath)
            throws Exception
    {
        //creates a blank presentation object that will contain the parsed presentation
        Presentation presentation = new Presentation();

        // parse an XML document into a DOM tree
        DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = parser.parse(new File(xmlPath));

        try {
            // create a SchemaFactory capable of understanding WXS schemas
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // load a WXS schema, represented by a Schema instance
            Source schemaFile = new StreamSource(new File(schemaPath));
            Schema schema = factory.newSchema(schemaFile);

            // create a Validator instance, which can be used to validate an instance document
            Validator validator = schema.newValidator();
            if (validator != null) {
                validator.validate(new DOMSource(document.getParentNode()));
            }
        }
        catch (Exception e) {
            System.err.println("Document could not be validated");
            System.err.println(e);
            throw e;
        }


        NodeList slideList = document.getElementsByTagName("Slide");
        NodeList slideElements;
        Node xmlSlide;
        Element defaults = document.getDocumentElement();

        NamedNodeMap presDefaults =  defaults.getAttributes();

        //sets the presentation defaults for font and colour
        getPresentationDefaults(presentation, presDefaults);

        //get the meta from the xml
        if(defaults.getElementsByTagName("Meta").item(0) != null)
        {
            //loop through all metas and add them to the presentation
            for(int m = 0; m < defaults.getElementsByTagName("Meta").getLength(); m++)
            {
                presentation.getMeta().add((getMeta(defaults.getElementsByTagName("Meta").item(m).getAttributes())));
            }
        }

        for(int m = 0; m < defaults.getElementsByTagName("POI").getLength(); m++)
        {
            Node node = defaults.getElementsByTagName("POI").item(m);
            // Only add top-level POI, their children will automatically be added
            if (node.getParentNode().getNodeName() != "POI") {
                presentation.getPOI().add(getPOI(node));
            }
        }

        // loop through all slide elements
        for (int i = 0; i < slideList.getLength(); i++) {
            //get the specified slide element from xml
            xmlSlide = slideList.item(i);
            Slide slide = new Slide();

            System.out.println(xmlSlide.getAttributes());
            Node xmlAttribPoi = xmlSlide.getAttributes().getNamedItem("poi");
            if (xmlAttribPoi != null) {
                slide.setPOI(presentation.getPoiWithId(xmlAttribPoi.getNodeValue()));
                // Extract string after #
                slide.setPoiId(xmlAttribPoi.getNodeValue().substring(1));
            }

            // sets the default values found in the presentation
            setSlideDefaults(slide, presentation);

            /*
            System.out.println("\n/////////////////// SLIDE: " + i + " ///////////////////");
            System.out.println("\nCurrent Element :" + xmlSlide.getNodeName() + " SLIDE : " + i);
            System.out.println("list size : " +slideList.getLength());*/

            //if node is actually an element
            if (xmlSlide.getNodeType() == Node.ELEMENT_NODE) {
                // pulls the slide attributes if it has any and will overwrite the default values from presentation
                getSlideDefaults(slide, xmlSlide.getAttributes());

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
                            //adds text object to the slide
                            slide.addElement(getText(slideElements.item(n), slide));
                        }

                        // if the element is shape, will pull that element from the xml
                        if(slideElements.item(n).getNodeName().equals("Shape"))
                        {
                            if(slideElements.item(n).hasAttributes())
                            {
                                //adds shape object to the slide
                                slide.addElement(getSlideShape(slideElements.item(n), slide));
                            }
                        }

                        // if the element is image, will pull that element from the xml
                        if(slideElements.item(n).getNodeName().equals("Image"))
                        {
                            if(slideElements.item(n).hasAttributes())
                            {
                                //adds image object to the slide
                                slide.addElement(getSlideImage(slideElements.item(n)));
                            }
                        }

                        // if the element is video, will pull that element from the xml
                        if(slideElements.item(n).getNodeName().equals("Video"))
                        {
                            if(slideElements.item(n).hasAttributes())
                            {
                                //adds video object to the slide
                                slide.addElement(getSlideVideo(slideElements.item(n)));
                            }
                        }

                        // if the element is audio, will pull that element from the xml
                        if(slideElements.item(n).getNodeName().equals("Audio"))
                        {
                            if(slideElements.item(n).hasAttributes())
                            {
                                //adds audio object to the slide
                                slide.addElement(getSlideAudio(slideElements.item(n)));
                            }
                        }
                    }
                }
            }

            // adds slide to the slide array in presentation
            presentation.getSlides().add(slide);
        }

        return presentation;
    }

    //pulls all audio information from the dom data structure and saves it into an audio object
    public static Audio getSlideAudio(Node xmlSlide)
    {
        PositionAttrib audioPositionAttrib = new PositionAttrib();

        audioPositionAttrib.setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        audioPositionAttrib.setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        audioPositionAttrib.setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        audioPositionAttrib.setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        Audio slideAudio = new Audio();

        slideAudio.setPath(xmlSlide.getAttributes().getNamedItem("path").getNodeValue());
        slideAudio.setPosition(audioPositionAttrib);

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
        PositionAttrib videoPositionAttrib = new PositionAttrib();

        videoPositionAttrib.setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        videoPositionAttrib.setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        videoPositionAttrib.setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        videoPositionAttrib.setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        Video slideVideo = new Video();

        slideVideo.setPath(xmlSlide.getAttributes().getNamedItem("path").getNodeValue());
        slideVideo.setPosition(videoPositionAttrib);

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
        slideShape.getColor().setFill(slide.getColor().getFill());
        slideShape.getColor().setColor(slide.getColor().getColor());

        // if the colour has been specified in the xml, it will overwrite the defaults
        if(xmlSlide.getAttributes().getNamedItem("color") != null)
        {
            slideShape.getColor().setColor(xmlSlide.getAttributes().getNamedItem("color").getNodeValue());
        }

        // if the colour fill has been specified in the xml, it will overwrite the defaults
        if(xmlSlide.getAttributes().getNamedItem("fill") != null)
        {
            slideShape.getColor().setFill(xmlSlide.getAttributes().getNamedItem("fill").getNodeValue());
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
        System.out.println("   colour: " + slideShape.getColor().getColor());
        System.out.println("   fill: " + slideShape.getColor().getFill());

        return slideShape;
    }

    //pulls all image information from the dom data structure and saves it into an image object
    public static Image getSlideImage(Node xmlSlide)
    {
        PositionAttrib imagePositionAttrib = new PositionAttrib();

        imagePositionAttrib.setxTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x").getNodeValue()));
        imagePositionAttrib.setyTopLeft(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y").getNodeValue()));
        imagePositionAttrib.setxBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("x2").getNodeValue()));
        imagePositionAttrib.setyBottomRight(Integer.parseInt(xmlSlide.getAttributes().getNamedItem("y2").getNodeValue()));

        Image slideImage = new Image();

        slideImage.setPath(xmlSlide.getAttributes().getNamedItem("path").getNodeValue());
        slideImage.setPosition(imagePositionAttrib);

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

    //gets the attributes for the TextFormat object element in xml
    public static void getTextContentAttributes(TextFormat content, NamedNodeMap xmlSlide)
    {
        if(xmlSlide.getNamedItem("fill") != null) {
            content.getColor().setFill(xmlSlide.getNamedItem("fill").getNodeValue());
        }

        if(xmlSlide.getNamedItem("color") != null) {
            content.getColor().setColor(xmlSlide.getNamedItem("color").getNodeValue());
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
            content.getFont().setFontWithName(xmlSlide.getNamedItem("font").getNodeValue());
        }
    }

    //sets the defaults from the slide/presentation for the text content objects
    //ensures text always has a format
    public static void setTextContentDefaults(TextFormat content, Slide slide)
    {
        content.getFont().setBold(slide.getFont().isBold());
        content.getFont().setUnderline(slide.getFont().isUnderline());
        content.getFont().setItalic(slide.getFont().isItalic());
        content.getFont().setTextSize(slide.getFont().getTextSize());
        content.getFont().setFontWithName(slide.getFont().getFontName());

        content.getColor().setColor(slide.getColor().getColor());
        content.getColor().setFill(slide.getColor().getFill());
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
                TextFormat contentText = new TextFormat();

                //sets the defaults for a new TextFormat from slide/presentation defaults
                setTextContentDefaults(contentText, slide);

                //gets the attributes specified from the Text element, these will overwrite the defaults when needed
                getTextContentAttributes(contentText, xmlSlide.getAttributes());

                ///////////////////BOLD/////////////////////////////////////////////////
                //if a format change is specified for bold this particular TextFormat will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("bold") != null) {
                    contentText.getFont().setBold(parseBoolean(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("bold").getNodeValue()));
                }

                /////////////////////underline//////////////////////////////////////////
                //if a format change is specified for underline this particular TextFormat will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("underline") != null) {
                    contentText.getFont().setUnderline(parseBoolean(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("underline").getNodeValue()));
                }

                ////////////////////italic//////////////////////////////////////////////
                //if a format change is specified for italic this particular TextFormat will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("italic") != null) {
                    contentText.getFont().setItalic(parseBoolean(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("italic").getNodeValue()));
                }

                ///////////////////textsize////////////////////////////////////////////
                //if a format change is specified for textsize this particular TextFormat will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("textsize") != null) {
                    contentText.getFont().setTextSize(Integer.parseInt(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("textsize").getNodeValue()));
                }

                ///////////////////////font///////////////////////////////////////////
                //if a format change is specified for font this particular TextFormat will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("font") != null) {
                    contentText.getFont().setFontWithName(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("font").getNodeValue());
                }

                //////////////////colour/////////////////////////////////////////////
                //if a format change is specified for colour this particular TextFormat will use that format
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Format") && xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("color") != null) {
                    contentText.getColor().setColor(xmlSlide.getChildNodes().item(i).getAttributes().getNamedItem("color").getNodeValue());
                }

                ///////////////////////line break///////////////////////////////////
                //when a line break as been specified the content is set to be a line break
                if (xmlSlide.getChildNodes().item(i).getNodeName().equals("Br")) {
                    contentText.setContent("\n");
                }
                else
                {
                    //if not a line break, the actual text content of the line is added to the TextFormat object
                    contentText.setContent(xmlSlide.getChildNodes().item(i).getTextContent().trim());
                }

                slideText.getContent().add(i, contentText);
            }


        return slideText;
    }

    //gets the meta from the dom structure and creates a meta object
    public static POI getPOI(Node node)
    {
        NamedNodeMap nodeMap = node.getAttributes();
        NodeList childNodes = node.getChildNodes();

        List<POI> children = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName() == "POI") {
                children.add(getPOI(childNodes.item(i)));
            }
        }

        String type;
        if (nodeMap.getNamedItem("type") == null) {
            if (node.getParentNode().getNodeName().equals("POI")) {
                type = "SPOI";
            }
            else {
                type = "POI";
            }
        }
        else {
            type = nodeMap.getNamedItem("type").getNodeValue();
        }

        return new POI(
                nodeMap.getNamedItem("id").getNodeValue(),
                Double.parseDouble(nodeMap.getNamedItem("latitude").getNodeValue()),
                Double.parseDouble(nodeMap.getNamedItem("longitude").getNodeValue()),
                type,
                nodeMap.getNamedItem("name").getNodeValue(),
                children.toArray(new POI[0])
        );
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
    public static void getPresentationDefaults(Presentation presentation, NamedNodeMap xmlDefaults)
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
            presentation.getPresDefaultColor().setColor(xmlDefaults.getNamedItem("color").getNodeValue());
            System.out.println("PRES colour: " + presentation.getPresDefaultColor().getColor());
        }

        if(xmlDefaults.getNamedItem("font") != null)
        {
            presentation.getPresDefaultFont().setFontWithName(xmlDefaults.getNamedItem("font").getNodeValue());
            System.out.println("PRES font: " + presentation.getPresDefaultFont().getFontName());
        }

        if(xmlDefaults.getNamedItem("fill") != null)
        {
            presentation.getPresDefaultColor().setFill(xmlDefaults.getNamedItem("fill").getNodeValue());
            System.out.println("PRES fill: " + presentation.getPresDefaultColor().getFill());
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
            slide.getColor().setFill(xmlSlide.getNamedItem("fill").getNodeValue());
            System.out.println("SLIDE fill: " + slide.getColor().getFill());
        }

        if(xmlSlide.getNamedItem("color") != null) {
            slide.getColor().setColor(xmlSlide.getNamedItem("color").getNodeValue());
            System.out.println("SLIDE colour: " + slide.getColor().getColor());
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
            slide.getFont().setFontWithName(xmlSlide.getNamedItem("font").getNodeValue());
            System.out.println("PRES font: " + slide.getFont().getFontName());
        }
    }

    //sets the slide defaults from the presentation defaults
    public static void setSlideDefaults(Slide slide, Presentation presentation)
    {
        slide.getFont().setFontWithName(presentation.getPresDefaultFont().getFontName());
        slide.getFont().setTextSize(presentation.getPresDefaultFont().getTextSize());
        slide.getFont().setItalic(presentation.getPresDefaultFont().isItalic());
        slide.getFont().setBold(presentation.getPresDefaultFont().isBold());
        slide.getFont().setUnderline(presentation.getPresDefaultFont().isUnderline());

        slide.getColor().setColor(presentation.getPresDefaultColor().getColor());
        slide.getColor().setFill(presentation.getPresDefaultColor().getFill());
    }

}
