package models;

public class GPS {

    double longitude;
    double latitude;
    double elevation;

    GPS(double longitude, double latitude, double elevation)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.elevation = elevation;
    }

    public GPS()
    {
        longitude = 0;
        latitude = 0;
        elevation = 0;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getElevation() {
        return elevation;
    }

    public void setElevation(double elevation) {
        this.elevation = elevation;
    }
}
