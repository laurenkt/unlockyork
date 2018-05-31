package models;
import java.lang.*;


public class GPSToPixel {

    // These are private to the class, static means that they only have their value set once
    static double inputLatitude = 53.955780;
    static double inputLongitude = -1.080076;

    static double widthInPixels = 1000;
    static double heightInPixels = 1000;

    static double topLeftLatitude = 53.978937;
    static double topLeftLongitude = -1.114124;

    static double bottomRightLatitude = 53.939026;
    static double bottomRightLongitude = -1.045646;

    // The function to do the conversion note it is static which means you don't need to make an instance of the GPSToPixel class to be able to use it
    public static Point GPSToPixel(GPS gps) {

// These are local variable to the method GPSToPixel
        double longitude = gps.getLongitude(); // left and right
        double latitude = gps.getLatitude(); // up and down


        double mapTopLeftLatitude;
        double mapTopLeftLongitude;
        double mapBottomRightLatitude;
        double mapBottomRightLongitude;


        double inputDifferenceLatitude = (topLeftLatitude - inputLatitude);
        double inputDifferenceLongitude = (topLeftLongitude - inputLongitude);

        double pixelsPerLatitude = (((Math.abs(topLeftLatitude)) - (Math.abs(bottomRightLatitude))) / heightInPixels);
        double pixelsPerLongitude = ((Math.abs(topLeftLongitude) - (Math.abs(bottomRightLongitude))) / widthInPixels);

        double pixelY = Math.round(inputDifferenceLatitude / pixelsPerLatitude);
        double pixelX = Math.round(Math.abs(inputDifferenceLongitude) / pixelsPerLongitude);

        return new Point(pixelX, pixelY);
    }

}
