package models;

import java.util.ArrayList;

public class Slide {
    Fonts font;
    Colours colour;
    Transitions transitions;

    //elements
    ArrayList<Text> text;
    ArrayList<Shape> shape;
    ArrayList<Image> image;
    ArrayList<Audio> audio;
    ArrayList<Video> video;


    Slide(Fonts font, Colours colour, Transitions transitions, ArrayList<Text> text, ArrayList<Shape> shape, ArrayList<Image> image, ArrayList<Audio> audio, ArrayList<Video> video) {
        this.font = font;
        this.colour = colour;
        this.transitions = transitions;
        this.text = text;
        this.shape = shape;
        this.image = image;
        this.audio = audio;
        this.video = video;
    }

    public Slide()
    {
        font = new Fonts();
        colour = new Colours();
        transitions = new Transitions();

        text = new ArrayList<Text>();
        shape = new ArrayList<Shape>();
        image = new ArrayList<Image>();
        audio = new ArrayList<Audio>();
        video = new ArrayList<Video>();
    }

    public Fonts getFont() {
        return font;
    }

    public void setFont(Fonts font) {
        this.font = font;
    }

    public Colours getColour() {
        return colour;
    }

    public void setColour(Colours colour) {
        this.colour = colour;
    }

    public Transitions getTransitions() {
        return transitions;
    }

    public void setTransitions(Transitions transitions) {
        this.transitions = transitions;
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
