package models;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class PresentationTest {

    @Test
    public void shouldFindPOIwithCorrectId() {
        POI poi1 = new POI("test", 0, 0, "POI", "Test", new POI[0]);
        POI poi2 = new POI("test2", 0, 0, "POI", "Test", new POI[0]);
        POI poi3 = new POI("test3", 0, 0, "POI", "Test", new POI[0]);

        Presentation p = new Presentation();
        p.getPOI().add(poi1);
        p.getPOI().add(poi2);
        p.getPOI().add(poi3);

        assertSame(poi1, p.getPoiWithId("#test"));
        assertSame(poi2, p.getPoiWithId("#test2"));
        assertSame(poi3, p.getPoiWithId("#test3"));
        assertNotSame(poi2, p.getPoiWithId("#test"));
        assertNull(p.getPoiWithId("#unknown"));
    }

}
