package models;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

public class POI {

    private double x;
    private double y;
    private String id;
    private List<POI> subPOI = new ArrayList<>();
    private double latitude;
    private double longitude;
    private String type;
    private String name = "Unnamed";

    public POI(String id, double latitude, double longitude, String type, POI ... children) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        //-1.101,53.9419,-1.0401,53.9667
        this.setPoint(this.gpsToPoint(latitude, longitude,
                53.9667,-1.101,
                53.9419,-1.0401,
                6000, 4155));

        this.type = type;

        for (int i = 0; i < children.length; i++) {
            subPOI.add(children[i]);
        }
    }

    public List<POI> getSubPOI() {
        return subPOI;
    }

    public POI(double x, double y, String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void setPoint(Point2D point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
