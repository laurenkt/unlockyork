package models;

public class Shape {

    String shape; //what the shape is ie. triangle

    int stroke; //thickness of shape outline

    PositionAttrib positionAttrib; // positionAttrib of the shape on the slide

    ColorAttrib colour; // color of the shape

    Shape(String shape, int stroke, PositionAttrib positionAttrib, ColorAttrib colour) {
        this.shape = shape;
        this.stroke = stroke;
        this.positionAttrib = positionAttrib;
        this.colour = colour;
    }

    public Shape()
    {
        shape = "null";
        stroke = 1;
        positionAttrib = new PositionAttrib();
        colour = new ColorAttrib();
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public int getStroke() {
        return stroke;
    }

    public void setStroke(int stroke) {
        this.stroke = stroke;
    }

    public PositionAttrib getPosition() {
        return positionAttrib;
    }

    public void setPosition(PositionAttrib positionAttrib) {
        this.positionAttrib = positionAttrib;
    }

    public ColorAttrib getColour() {
        return colour;
    }

    public void setColour(ColorAttrib colour) {
        this.colour = colour;
    }
}
