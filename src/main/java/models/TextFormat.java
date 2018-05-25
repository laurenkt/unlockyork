package models;

public class TextFormat {

    FontAttrib font; // font for this part of text
    ColorAttrib colour; // color for this part of text
    String content; // actual text content

    TextFormat(FontAttrib font, ColorAttrib colour, String content)
    {
        this.font = font;
        this.colour = colour;
        this.content = content;
    }

    public TextFormat()
    {
        font = new FontAttrib();
        colour = new ColorAttrib();
        content = "null";
    }

    public FontAttrib getFont() {
        return font;
    }

    public void setFont(FontAttrib font) {
        this.font = font;
    }

    public ColorAttrib getColor() {
        return colour;
    }

    public void setColour(ColorAttrib colour) {
        this.colour = colour;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
