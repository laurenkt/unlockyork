package models;
public class Text {

    Position position;
    Fonts font;
    Colours colour;
    String content;

    Text(Position position, Fonts font, Colours colour, String content) {
        this.position = position;
        this.font = font;
        this.colour = colour;
        this.content = content;
    }

    public Text()
    {
        position = new Position();
        font = new Fonts();
        colour = new Colours();
        content = "null";
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Fonts getFont() {
        return font;
    }

    public void setFont(Fonts font) {
        this.font = font;
    }

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
