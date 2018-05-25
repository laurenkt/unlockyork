package models;

import java.util.ArrayList;

public class Text implements SlideElement {

    PositionAttrib positionAttrib; // positionAttrib of text on this slide

    ArrayList<TextFormat> content; // array of all the different parts of the text

    TransitionAttrib transition; // transitionAttrib for the text

    Text(PositionAttrib positionAttrib, ArrayList<TextFormat> content, TransitionAttrib transition) {
        this.positionAttrib = positionAttrib;
        this.content = content;
        this.transition = transition;
    }

    public Text()
    {
        positionAttrib = new PositionAttrib();
        content = new ArrayList<TextFormat>();
        transition = new TransitionAttrib();
    }

    public TransitionAttrib getTransition() {
        return transition;
    }

    public void setTransition(TransitionAttrib transition) {
        this.transition = transition;
    }

    public PositionAttrib getPosition() {
        return positionAttrib;
    }

    public void setPosition(PositionAttrib positionAttrib) {
        this.positionAttrib = positionAttrib;
    }

    public ArrayList<TextFormat> getContent() {
        return content;
    }

    public void setContent(ArrayList<TextFormat> content) {
        this.content = content;
    }
}
