package models;
public class Position
{

    int xTopLeft; //top left corner x value
    int yTopLeft; //top left corner y value

    int xBottomRight; //bottom right corner x value
    int yBottomRight; //bottom right corner y value

    Position(int xTopLeft, int yTopLeft, int xBottomRight, int yBottomRight)
    {
        this.xTopLeft = xTopLeft;
        this.yTopLeft = yTopLeft;
        this.xBottomRight = xBottomRight;
        this.yBottomRight = yBottomRight;
    }

    public Position()
    {
        xTopLeft = 0;
        yTopLeft = 0;

        xBottomRight = 0;
        yBottomRight = 0;
    }

    public int getxTopLeft() {
        return xTopLeft;
    }

    public int getyTopLeft() {
        return yTopLeft;
    }

    public int getxBottomRight() {
        return xBottomRight;
    }

    public int getyBottomRight() {
        return yBottomRight;
    }

    public void setxTopLeft(int xTopLeft) {
        this.xTopLeft = xTopLeft;
    }

    public void setyTopLeft(int yTopLeft) {
        this.yTopLeft = yTopLeft;
    }

    public void setxBottomRight(int xBottomRight) {
        this.xBottomRight = xBottomRight;
    }

    public void setyBottomRight(int yBottomRight) {
        this.yBottomRight = yBottomRight;
    }
}



