package models;

public class Shape {

    String shape;

    int stroke;

    Position position;

    Colours colour;

    public Shape(String shape, int stroke, Position position, Colours colour) {
        this.shape = shape;
        this.stroke = stroke;
        this.position = position;
        this.colour = colour;
    }
}
