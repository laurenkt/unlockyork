package models;

import java.awt.geom.Point2D;

public class POI {

    Point2D location;
    String name;
    double latitude;
    double longitude;

    public POI() {
        location = null;
        name = "Not Set";
    }

    public POI(Point2D location, String name) {
        this.location = location;
        this.name = name;
    }

    public POI(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
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
