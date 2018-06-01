package models;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class POI {

    double x;
    double y;
    String name;
    List<POI> subPOI = new ArrayList<>();
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
        //-1.101,53.9419,-1.0401,53.9667
        this.setPoint(this.gpsToPoint(latitude, longitude,
                53.9667,-1.101,
                53.9419,-1.0401,
                6000, 4155));
    }

    public List<POI> getSubPOI() {
        return subPOI;
    }

    public POI(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public void setPoint(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
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

    public Point2D gpsToPoint(double latitude, double longitude, double topLatitude, double leftLongitude, double bottomLatitude, double rightLongitude, double mapWidth, double mapHeight) {
        double inputDifferenceLatitude = topLatitude - latitude;
        double inputDifferenceLongitude = leftLongitude - longitude;

        double pixelsPerLatitude = (Math.abs(topLatitude) - Math.abs(bottomLatitude)) / mapHeight;
        double pixelsPerLongitude = (Math.abs(leftLongitude) - Math.abs(rightLongitude)) / mapWidth;

        double pixelY = Math.round(inputDifferenceLatitude / pixelsPerLatitude);
        double pixelX = Math.round(Math.abs(inputDifferenceLongitude) / pixelsPerLongitude);

        return new Point2D(pixelX, pixelY);
    }
}
