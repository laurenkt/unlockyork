package models;
public class Colours {

    String colour; //specifies colour as a 6 digit string
    String fill; //specifies fill, ie gradients

    Colours(String colour, String fill) {
        this.colour = colour;
        this.fill = fill;
    }

    public Colours()
    {
        colour = "null";
        fill = "null";
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getFill() {
        return fill;
    }

    public void setFill(String fill) {
        this.fill = fill;
    }
}
