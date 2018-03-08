package models;
public class Slide {
    Fonts font;
    Colours colour;
    Transitions transitions;

    //elements
    Text text;
    Shape shape;
    Image image;
    Audio audio;
    Video video;


    public Slide(Fonts font, Colours colour, Transitions transitions, Text text, Shape shape, Image image, Audio audio, Video video) {
        this.font = font;
        this.colour = colour;
        this.transitions = transitions;
        this.text = text;
        this.shape = shape;
        this.image = image;
        this.audio = audio;
        this.video = video;
    }
}
