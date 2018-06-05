package models;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

public class SlideTest {

    @Test
    public void shouldFindCorrectMaxX2() {
        Slide slide = new Slide();
        slide.addElement(new Text(new PositionAttrib(0, 0, 999, 10), new ArrayList<>(0), null));

        assertEquals(999.0, slide.getMaxX2());

        slide.addElement(new Text(new PositionAttrib(50, 0, 1100, 0), new ArrayList<>(0), null));

        assertEquals(1100.0, slide.getMaxX2());
    }

    @Test
    public void shouldFindCorrectMaxY2() {
        Slide slide = new Slide();
        slide.addElement(new Text(new PositionAttrib(0, 0, 999, 10), new ArrayList<>(0), null));

        assertEquals(10.0, slide.getMaxY2());

        slide.addElement(new Text(new PositionAttrib(50, 0, 1100, 0), new ArrayList<>(0), null));

        assertEquals(10.0, slide.getMaxY2());

        slide.addElement(new Text(new PositionAttrib(50, 0, 1100, 333), new ArrayList<>(0), null));

        assertEquals(333.0, slide.getMaxY2());
    }
}
