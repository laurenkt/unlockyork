package models;

import javafx.geometry.Point2D;

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
        //-1.101,53.9419,-1.0401,53.9667
        this.setPoint(this.gpsToPoint(latitude, longitude,
                53.9667,-1.101,53.9419,-1.0401,
                6000, 4155));
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

    public Point2D gpsToPoint(double latitude, double longitude, double mapTopLeftLatitude, double mapTopLeftLongitude, double mapBottomRightLatitude, double mapBottomRightLongitude, double mapHeight, double mapWidth) {
        double inputDifferenceLatitude = (mapTopLeftLatitude - latitude);
        double inputDifferenceLongitude = (mapTopLeftLongitude - longitude);

        double pixelsPerLatitude = (((Math.abs(mapTopLeftLatitude)) - (Math.abs(mapBottomRightLatitude))) / mapHeight);
        double pixelsPerLongitude = ((Math.abs(mapTopLeftLongitude) - (Math.abs(mapBottomRightLongitude))) / mapWidth);

        double pixelY = Math.round(inputDifferenceLatitude / pixelsPerLatitude);
        double pixelX = Math.round(Math.abs(inputDifferenceLongitude) / pixelsPerLongitude);

        return new Point2D(pixelX, pixelY);
    }
}
