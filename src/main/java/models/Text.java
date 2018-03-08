package models;
public class Text {

    Position position;
    Fonts font;
    Colours colour;

    String content;

    public Text(Position position, Fonts font, Colours colour, String content) {
        this.position = position;
        this.font = font;
        this.colour = colour;
        this.content = content;
    }
}
