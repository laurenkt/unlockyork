package models;

import java.awt.geom.Point2D;

public class POI {

    double x;
    double y;
    String name;
    double latitude;
    double longitude;

    public POI() {
        x = 0;
        y = 0;
        name = "Not Set";
    }

    public POI(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    POI(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
