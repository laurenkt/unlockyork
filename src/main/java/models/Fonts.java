package models;
public class Fonts {

    String font;

    boolean italic;
    boolean bold;
    boolean underline;

    int textSize;

    Fonts(String font, boolean italic, boolean bold, boolean underline, int textSize) {
        this.font = font;
        this.italic = italic;
        this.bold = bold;
        this.underline = underline;
        this.textSize = textSize;
    }

    public Fonts()
    {
        font = "Times New Roman";

        italic = false;
        bold = false;
        underline = false;

        textSize = 10;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
