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

    private static POI[] emptyPOI = new POI[0];

    public POI(String id, double latitude, double longitude, String type, String name, POI ... children) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        //-1.101,53.9419,-1.0401,53.9667
        this.setPoint(POI.latLongToPoint(latitude, longitude));

        this.type = type;
        this.name = name;

        // adds the sPOI objects to the POI object
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

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

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

    static public Point2D latLongToPoint(double latitude, double longitude) {
       return POI.gpsToPoint(latitude, longitude,
                53.9667,-1.101,
                53.9419,-1.0401,
                6000, 4155);
    }

    static public Point2D gpsToPoint(double latitude, double longitude, double topLatitude, double leftLongitude, double bottomLatitude, double rightLongitude, double mapWidth, double mapHeight) {
        double inputDifferenceLatitude = topLatitude - latitude;
        double inputDifferenceLongitude = leftLongitude - longitude;

        double pixelsPerLatitude = (Math.abs(topLatitude) - Math.abs(bottomLatitude)) / mapHeight;
        double pixelsPerLongitude = (Math.abs(leftLongitude) - Math.abs(rightLongitude)) / mapWidth;

        double pixelY = Math.round(inputDifferenceLatitude / pixelsPerLatitude);
        double pixelX = Math.round(Math.abs(inputDifferenceLongitude) / pixelsPerLongitude);

        return new Point2D(pixelX, pixelY);
    }
}
