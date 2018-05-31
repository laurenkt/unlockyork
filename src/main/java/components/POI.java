package components;

import java.awt.geom.Point2D;

public class POI {

    Point2D location;
    String name;

    public POI() {
        location = null;
        name = "Not Set";
    }

    POI(Point2D location, String name) {
        this.location = location;
        this.name = name;
    }

    public Point2D getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }
}
