package models;

import java.util.ArrayList;
import java.util.List;

public class Presentation {
    List<Meta> meta; //presentation meta
    List<Slide> slides; //all slides inside presentation
    GPS gps; //GPS coordinates for the presentation
    FontAttrib presDefaultFont; // presentation defaults for the font of text
    ColorAttrib presDefaultColour; // presentation defaults for color
    List<POI> POIs = new ArrayList<>();

    public Presentation() {
        meta = new ArrayList<>();
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

    public List<Slide> getSlides() {
        return slides;
    }

    public void setSlides(List<Slide> slides) {
        this.slides = slides;
    }

    public List<Meta> getMeta() {
        return meta;
    }

    public void setMeta(List<Meta> meta) {
        this.meta = meta;
    }

    public GPS getGps() {
        return gps;
    }

    public void setGps(GPS gps) {
        this.gps = gps;
    }

    public List<POI> getPOI() {
        return POIs;
    }

    public POI getPoiWithId(String id) {
        String realId = id.substring(1);
        for (POI poi : POIs) {
            if (poi.getId().equals(realId))
                return poi;
        }
        return null;
    }
}


