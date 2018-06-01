package components;

import java.awt.geom.Point2D;

public class POI {

    double x;
    double y;
    String name;

    public POI() {
        x = 0;
        y = 0;
        name = "Not Set";
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
