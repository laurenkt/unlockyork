package models;
public class Fonts {

    String font;

    boolean italic;
    boolean bold;
    boolean underline;

    int textSize;

    public Fonts(String font, boolean italic, boolean bold, boolean underline, int textSize) {
        this.font = font;
        this.italic = italic;
        this.bold = bold;
        this.underline = underline;
        this.textSize = textSize;
    }
}
