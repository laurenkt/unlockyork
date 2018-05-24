package models;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Fonts {

    String fontName;

    boolean italic;
    boolean bold;
    boolean underline;

    int textSize;

    Fonts(String font, boolean italic, boolean bold, boolean underline, int textSize) {
        this.fontName = font;
        this.italic = italic;
        this.bold = bold;
        this.underline = underline;
        this.textSize = textSize;
    }

    public Fonts()
    {
        fontName = "Times New Roman";

        italic = false;
        bold = false;
        underline = false;

        textSize = 10;
    }

    public Font getFont() {
        return Font.font(
            fontName,
            bold ? FontWeight.BOLD : FontWeight.NORMAL,
            italic ? FontPosture.ITALIC : FontPosture.REGULAR,
            textSize);
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontWithName(String font) {
        this.fontName = font;
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
