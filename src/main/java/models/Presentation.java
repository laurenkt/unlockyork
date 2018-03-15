package models;

import java.awt.*;
import java.util.ArrayList;

public class Presentation {
    Meta meta;
    ArrayList<Slide> slides;
    GPS gps;
    Fonts presDefaultFont;
    Colours presDefaultColour;


    Presentation(Meta meta, ArrayList<Slide> slides, GPS gps, Fonts presDefaultFont, Colours presDefaultColour) {
        this.meta = meta;
        this.slides = slides;
        this.gps = gps;
        this.presDefaultFont = presDefaultFont;
        this.presDefaultColour = presDefaultColour;
    }

    public Presentation()
    {
        meta = new Meta();
        slides = new ArrayList<Slide>();
        gps = new GPS();
        presDefaultFont = new Fonts();
        presDefaultColour = new Colours();
    }

    public Fonts getPresDefaultFont() {
        return presDefaultFont;
    }

    public void setPresDefaultFont(Fonts presDefaultFont) {
        this.presDefaultFont = presDefaultFont;
    }

    public Colours getPresDefaultColour() {
        return presDefaultColour;
    }

    public void setPresDefaultColour(Colours presDefaultColour) {
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


