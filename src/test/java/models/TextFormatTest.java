package models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TextFormatTest {

    @Test
    public void getContentShouldNotAlterValidWhitespace() {
        TextFormat format = new TextFormat();
        format.setContent("Some example content");
        assertEquals("Some example content", format.getContent());
    }

    @Test
    public void getContentShouldStripExcessNewlinesPreservingSpaces() {
        TextFormat format = new TextFormat();
        format.setContent("Some\n\nexample content");
        assertEquals("Some example content", format.getContent());
    }

    @Test
    public void getContentShouldStripExcessSpaces() {
        TextFormat format = new TextFormat();
        format.setContent("Some  example content");
        assertEquals("Some example content", format.getContent());
    }
}
