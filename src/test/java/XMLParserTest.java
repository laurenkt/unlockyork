import models.Presentation;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class XMLParserTest {

    Presentation presentation;

    {
        try {
            presentation = XMLParser.parse("src/test/resources/example.pws", "src/build/resources/main/schema.xsd");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XMLParser.ValidationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPresentationDefaults() {
        //test correct colour has been found
        assertEquals("#F341DE", presentation.getPresDefaultColor().getColor());
        //test correct font name has been found
        assertEquals("Times New Roman", presentation.getPresDefaultFont().getFontName());
        //test if correct bold is found
        assertEquals(false, presentation.getPresDefaultFont().isBold());
        //test if correct italic is found
        assertEquals(false, presentation.getPresDefaultFont().isItalic());
        //test if correct underline has been found
        assertEquals(false, presentation.getPresDefaultFont().isUnderline());
        //test if correct text size has been found
        assertEquals(12, presentation.getPresDefaultFont().getTextSize());
    }

    @Test
    public void testMeta(){
        //test that correct number of meta has been found
        assertEquals(1, presentation.getMeta().size());
        //test correct key has been found
        assertEquals("author", presentation.getMeta().get(0).getKey());
        //test correct value has been found
        assertEquals("Stuart Porter", presentation.getMeta().get(0).getValue());
    }

    @Test
    public void testSlides(){
        //test the correct number of slides has been found
        assertEquals(5, presentation.getSlides().size());
        //test if slide 0 transitions in set at duration 12
        assertEquals(12, presentation.getSlides().get(0).getTransitions().getDuration());
        //test slide 2 has a fill colour of #023211
        assertEquals("#023211", presentation.getSlides().get(2).getColor().getFill());
        //test slide 2 has a colour of #F341DE
        assertEquals("#F341DE", presentation.getSlides().get(2).getColor().getColor());
    }

    @Test
    public void testText(){
        //test first text field of first slide - textsize = 18
        assertEquals(12, presentation.getSlides().get(0).getText().get(0).getContent().get(0).getFont().getTextSize());
        //test first text field of first slide - underline = true
        assertEquals(true, presentation.getSlides().get(0).getText().get(0).getContent().get(0).getFont().isUnderline());
        //test first text field of first slide - font = Times New Roman
        assertEquals("Times New Roman", presentation.getSlides().get(0).getText().get(0).getContent().get(0).getFont().getFontName());
        //test first text field of first slide - x position = 0
        assertEquals(0, presentation.getSlides().get(0).getText().get(0).getPosition().getxTopLeft());
        //test first text field of first slide - y position = 0
        assertEquals(0, presentation.getSlides().get(0).getText().get(0).getPosition().getyTopLeft());
        //test first text field of first slide - content = This is the schema of the
        assertEquals("This is the schema of the", presentation.getSlides().get(0).getText().get(0).getContent().get(0).getContent());
    }

    @Test
    public void testShape(){
        //test slide 1 shape = ellipse
        assertEquals("ellipse", presentation.getSlides().get(1).getShape().get(0).getShape());
        assertEquals("gradient(#AEDE38,#3482ED)" ,presentation.getSlides().get(1).getShape().get(0).getColor().getFill());
    }

    @Test
    public void testImage(){
        assertEquals("./local_file.jpg", presentation.getSlides().get(3).getImage().get(0).getPath());
        assertEquals(10, presentation.getSlides().get(3).getImage().get(0).getPosition().x1);
        assertEquals(200, presentation.getSlides().get(3).getImage().get(0).getPosition().y2);
    }

    @Test
    public void testVideo(){
        assertEquals("./local_file.mp4", presentation.getSlides().get(4).getVideo().get(0).getPath());
        assertEquals(10, presentation.getSlides().get(4).getVideo().get(0).getPosition().x1);
        assertEquals(515, presentation.getSlides().get(4).getVideo().get(0).getPosition().y2);
    }

    @Test
    public void testAudio(){
        assertEquals("./local_file.wav", presentation.getSlides().get(4).getAudio().get(0).getPath());
        assertEquals(0, presentation.getSlides().get(4).getAudio().get(0).getPosition().x1);
        assertEquals(10, presentation.getSlides().get(4).getAudio().get(0).getPosition().y2);
    }

}



