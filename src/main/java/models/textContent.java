package models;

public class textContent {

    Fonts font; // font for this part of text
    Colours colour; // colour for this part of text
    String content; // actual text content

    textContent(Fonts font, Colours colour, String content)
    {
        this.font = font;
        this.colour = colour;
        this.content = content;
    }

    public textContent()
    {
        font = new Fonts();
        colour = new Colours();
        content = "null";
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
