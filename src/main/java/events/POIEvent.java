package events;

import javafx.event.Event;
import javafx.event.EventType;
import models.POI;

public class POIEvent extends Event {
    private POI poi;

    public POIEvent(POI poi) {
        super(EventType.ROOT);
        this.poi = poi;
    }

    public POI getPOI() {
        return this.poi;
    }
}
