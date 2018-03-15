import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class XMLParserTest {
    XMLParser parser;

    @Before
    public void setUp() {
        parser = new XMLParser();
    }

    @Test
    public void onePlusOneEqualsTwo() {
        assertTrue(1+1 == 2);
    }

}


