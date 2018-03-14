package models;

public class Shape {

    String shape;

    int stroke;

    Position position;

    Colours colour;

    Shape(String shape, int stroke, Position position, Colours colour) {
        this.shape = shape;
        this.stroke = stroke;
        this.position = position;
        this.colour = colour;
    }

    public Shape()
    {
        shape = "null";

        stroke = 0;

        position = new Position();

        colour = new Colours();
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }
}
