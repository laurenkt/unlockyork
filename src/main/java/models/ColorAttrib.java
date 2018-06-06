package models;

import javafx.scene.paint.Paint;

public class ColorAttrib {

    String color; //specifies color as a 6 digit string
    String fill; //specifies fill, ie gradients

    ColorAttrib(String color, String fill) {
        this.color = color;
        this.fill = fill;
    }

    public ColorAttrib()
    {
        color = "null";
        fill = "null";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }

    public Paint getColorPaint() {
        return Paint.valueOf(color);
    }
}
