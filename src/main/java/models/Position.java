package models;
public class Position
{

    int xTopLeft;
    int yTopLeft;

    int xBottomRight;
    int yBottomRight;

    Position(int xTopLeft, int yTopLeft, int xBottomRight, int yBottomRight)
    {
        this.xTopLeft = xTopLeft;
        this.yTopLeft = yTopLeft;
        this.xBottomRight = xBottomRight;
        this.yBottomRight = yBottomRight;
    }
}



