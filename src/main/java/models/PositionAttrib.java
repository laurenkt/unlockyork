package models;
public class PositionAttrib
{
    public double x1; //top left corner x value
    public double y1; //top left corner y value
    public double x2; //bottom right corner x value
    public double y2; //bottom right corner y value

    PositionAttrib(int x1, int y1, int x2, int y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public PositionAttrib()
    {
        x1 = 0;
        y1 = 0;

        x2 = 0;
        y2 = 0;
    }

    public double getxTopLeft() {
        return x1;
    }

    public double getyTopLeft() {
        return y1;
    }

    public double getxBottomRight() {
        return x2;
    }

    public double getyBottomRight() {
        return y2;
    }

    public void setxTopLeft(double xTopLeft) {
        this.x1 = xTopLeft;
    }

    public void setyTopLeft(double yTopLeft) {
        this.y1 = yTopLeft;
    }

    public void setxBottomRight(double xBottomRight) {
        this.x2 = xBottomRight;
    }

    public void setyBottomRight(double yBottomRight) {
        this.y2 = yBottomRight;
    }

    public double getWidth() {
        return Math.abs(x2 - x1);
    }

    public double getHeight() {
        return Math.abs(y2 - y1);
    }
}



