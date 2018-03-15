import models.Presentation;
import models.Shape;
import models.Slide;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class XMLParserTest {
    XMLParser parser;

    @Before
    public void setUp() throws Exception {
         parser = new XMLParser();
    }

    @Test
    public void testPresentation() {
        XMLParser parser = new XMLParser();
        Presentation presentation = parser.parser("src/build/resources/main/example.pws", "src/build/resources/main/schema.xsd");

        assertTrue(!presentation.getPresDefaultFont().isItalic() == false);

       // assertTrue(presentation.getPresDefaultFont().isUnderline() == false);
      //  assertTrue(presentation.getPresDefaultFont().getTextSize() == 12);
      //  assertTrue(presentation.getSlides().size() == 5);
    }

}



