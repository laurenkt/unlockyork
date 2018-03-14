package models;
public class Slide {
    Fonts font;
    Colours colour;
    Transitions transitions;

    //elements
    Text[] text;
    Shape[] shape;
    Image[] image;
    Audio[] audio;
    Video[] video;


    Slide(Fonts font, Colours colour, Transitions transitions, Text[] text, Shape[] shape, Image[] image, Audio[] audio, Video[] video) {
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

        text = new Text[0];
        shape = new Shape[0];
        image = new Image[0];
        audio = new Audio[0];
        video = new Video[0];
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

    public Text[] getText() {
        return text;
    }

    public void setText(Text[] text) {
        this.text = text;
    }

    public Shape[] getShape() {
        return shape;
    }

    public void setShape(Shape[] shape) {
        this.shape = shape;
    }

    public Image[] getImage() {
        return image;
    }

    public void setImage(Image[] image) {
        this.image = image;
    }

    public Audio[] getAudio() {
        return audio;
    }

    public void setAudio(Audio[] audio) {
        this.audio = audio;
    }

    public Video[] getVideo() {
        return video;
    }

    public void setVideo(Video[] video) {
        this.video = video;
    }
}
