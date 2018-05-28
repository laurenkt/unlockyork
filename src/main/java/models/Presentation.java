package models;

import java.util.ArrayList;

public class Presentation {
    Meta meta; //presentation meta
    ArrayList<Slide> slides; //all slides inside presentation
    GPS gps; //GPS coordinates for the presentation
    FontAttrib presDefaultFont; // presentation defaults for the font of text
    ColorAttrib presDefaultColour; // presentation defaults for color

    public Presentation() {
        meta = new Meta();
        slides = new ArrayList<>();
        gps = new GPS();
        presDefaultFont = new FontAttrib();
        presDefaultColour = new ColorAttrib();
    }

    public double getMaxX2() {
        return slides.stream()
                .mapToDouble(slide -> slide.getMaxX2())
                .max()
                .orElse(1920);
    }

    public double getMaxY2() {
        return slides.stream()
                .mapToDouble(slide -> slide.getMaxY2())
                .max()
                .orElse(1080);
    }

    public FontAttrib getPresDefaultFont() {
        return presDefaultFont;
    }

    public void setPresDefaultFont(FontAttrib presDefaultFont) {
        this.presDefaultFont = presDefaultFont;
    }

    public ColorAttrib getPresDefaultColor() {
        return presDefaultColour;
    }

    public void setPresDefaultColour(ColorAttrib presDefaultColour) {
        this.presDefaultColour = presDefaultColour;
    }

    public ArrayList<Slide> getSlides() {
        return slides;
    }

    public void setSlides(ArrayList<Slide> slides) {
        this.slides = slides;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }
}


