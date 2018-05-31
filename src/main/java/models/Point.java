package models;

public class Point {

    double X;
    double Y;

    Point(double X, double Y)
    {
        this.X = X;
        this.Y = Y;
    }

    public Point()
    {
        X = 0;
        Y = 0;
    }

    public double getX() {
        return X;
    }

    public void setX(double X) {
        this.X = X;
    }

    public double getY() {
        return Y;
    }

    public void Y(double Y) {
        this.Y = Y;
    }
}

