package models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class PositionAttribTest {

    @Test
    public void widthShouldBeCalculatedFromOffsets() {
        PositionAttrib attrib = new PositionAttrib(200, 0, 500, 0);
        assertEquals(300.0, attrib.getWidth());
    }

    @Test
    public void heightShouldBeCalculatedFromOffsets() {
        PositionAttrib attrib = new PositionAttrib(0, 100, 0, 0);
        assertEquals(100.0, attrib.getHeight());
    }
}
