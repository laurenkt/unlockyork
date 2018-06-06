package models;

public class TextFormat {

    FontAttrib font; // font for this part of text
    ColorAttrib color; // color for this part of text
    String content; // actual text content

    TextFormat(FontAttrib font, ColorAttrib color, String content)
    {
        this.font = font;
        this.color = color;
        this.content = content;
    }

    public TextFormat()
    {
        font = new FontAttrib();
        color = new ColorAttrib();
        content = "null";
    }

    public FontAttrib getFont() {
        return font;
    }

    public void setFont(FontAttrib font) {
        this.font = font;
    }

    public ColorAttrib getColor() {
        return color;
    }

    public void setColor(ColorAttrib color){
        this.color = color;
    }

    public String getContent() {
        // Strip excess whitespace (compress it)
        return content.replaceAll("([ \r\n]+)", " ");
    }

    public void setContent(String content) {
        this.content = content;
    }
}
