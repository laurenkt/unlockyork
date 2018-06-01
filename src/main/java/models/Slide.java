package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Slide {
    FontAttrib font; // font default for the slide
    ColorAttrib colour; // color default for the slide
    TransitionAttrib transitionAttrib; // transition set for the slide
    POI poi;

    //elements
    List<SlideElement> elements;
    List<Text> text; //all text objects for this slide
    List<Shape> shape; //all shape objects for this slide
    List<Image> image; //all image objects for this slide
    List<Audio> audio; //all audio objects for this slide
    List<Video> video; //all video objects for this slide

    public void setPOI(POI poi) {
        this.poi = poi;
    }

    public Slide()
    {
        font = new FontAttrib();
        colour = new ColorAttrib();
        transitionAttrib = new TransitionAttrib();

        elements = new ArrayList<>();
        text = new ArrayList<>();
        shape = new ArrayList<>();
        image = new ArrayList<>();
        audio = new ArrayList<>();
        video = new ArrayList<>();
    }

    public void addElement(SlideElement el) {
        elements.add(el);

        if (el instanceof Text)
            text.add((Text)el);
        if (el instanceof Shape)
            shape.add((Shape)el);
        if (el instanceof Image)
            image.add((Image)el);
        if (el instanceof Audio)
            audio.add((Audio)el);
        if (el instanceof Video)
            video.add((Video)el);
    }

    public double getMaxX2() {
        return elements.stream()
                .filter(el -> el instanceof Positionable)
                .mapToDouble(el -> ((Positionable) el).getPosition().x2)
                .max()
                .orElse(0);
    }

    public double getMaxY2() {
        return elements.stream()
                .filter(el -> el instanceof Positionable)
                .mapToDouble(el -> ((Positionable) el).getPosition().y2)
                .max()
                .orElse(0);
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

    public void setColor(ColorAttrib color) {
        this.colour = color;
    }

    public TransitionAttrib getTransitions() {
        return transitionAttrib;
    }

    public void setTransitions(TransitionAttrib transitionAttrib) {
        this.transitionAttrib = transitionAttrib;
    }

    public List<Text> getText() {
        return text;
    }

    public void setText(List<Text> text) {
        this.text = text;
    }

    public List<Shape> getShape() {
        return shape;
    }

    public void setShape(List<Shape> shape) {
        this.shape = shape;
    }

    public List<Image> getImage() {
        return image;
    }

    public void setImage(List<Image> image) {
        this.image = image;
    }

    public List<Audio> getAudio() {
        return audio;
    }

    public void setAudio(List<Audio> audio) {
        this.audio = audio;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public List<SlideElement> getElements() {
        return elements;
    }

    public POI getPOI() {
        return this.poi;
    }
}
