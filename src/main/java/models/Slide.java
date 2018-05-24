package models;

import java.util.ArrayList;

public class Slide {
    FontAttrib font; // font default for the slide
    ColorAttrib colour; // color default for the slide
    TransitionAttrib transitionAttrib; // transition set for the slide

    //elements
    ArrayList<Text> text; //all text objects for this slide
    ArrayList<Shape> shape; //all shape objects for this slide
    ArrayList<Image> image; //all image objects for this slide
    ArrayList<Audio> audio; //all audio objects for this slide
    ArrayList<Video> video; //all video objects for this slide


    Slide(FontAttrib font, ColorAttrib colour, TransitionAttrib transitionAttrib, ArrayList<Text> text, ArrayList<Shape> shape, ArrayList<Image> image, ArrayList<Audio> audio, ArrayList<Video> video) {
        this.font = font;
        this.colour = colour;
        this.transitionAttrib = transitionAttrib;
        this.text = text;
        this.shape = shape;
        this.image = image;
        this.audio = audio;
        this.video = video;
    }

    public Slide()
    {
        font = new FontAttrib();
        colour = new ColorAttrib();
        transitionAttrib = new TransitionAttrib();

        text = new ArrayList<Text>();
        shape = new ArrayList<Shape>();
        image = new ArrayList<Image>();
        audio = new ArrayList<Audio>();
        video = new ArrayList<Video>();
    }

    public FontAttrib getFont() {
        return font;
    }

    public void setFont(FontAttrib font) {
        this.font = font;
    }

    public ColorAttrib getColour() {
        return colour;
    }

    public void setColour(ColorAttrib colour) {
        this.colour = colour;
    }

    public TransitionAttrib getTransitions() {
        return transitionAttrib;
    }

    public void setTransitions(TransitionAttrib transitionAttrib) {
        this.transitionAttrib = transitionAttrib;
    }

    public ArrayList<Text> getText() {
        return text;
    }

    public void setText(ArrayList<Text> text) {
        this.text = text;
    }

    public ArrayList<Shape> getShape() {
        return shape;
    }

    public void setShape(ArrayList<Shape> shape) {
        this.shape = shape;
    }

    public ArrayList<Image> getImage() {
        return image;
    }

    public void setImage(ArrayList<Image> image) {
        this.image = image;
    }

    public ArrayList<Audio> getAudio() {
        return audio;
    }

    public void setAudio(ArrayList<Audio> audio) {
        this.audio = audio;
    }

    public ArrayList<Video> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<Video> video) {
        this.video = video;
    }
}
