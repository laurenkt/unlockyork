package models;

import java.util.ArrayList;

public class Text {

    Position position;

    ArrayList<textContent> content;

    Transitions transition;

    Text(Position position, ArrayList<textContent> content, Transitions transition) {
        this.position = position;
        this.content = content;
        this.transition = transition;
    }

    public Text()
    {
        position = new Position();
        content = new ArrayList<textContent>();
        transition = new Transitions();
    }

    public Transitions getTransition() {
        return transition;
    }

    public void setTransition(Transitions transition) {
        this.transition = transition;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<textContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<textContent> content) {
        this.content = content;
    }

//    public void printText()
//    {
//        System.out.println();
//
//        System.out.println("content TEXT , bold: " + content.getFont().isBold()
//                + " ,italic: " + content.getFont().isItalic()
//                + " ,underline: " + content.getFont().isUnderline()
//                + " ,textsize: " + content.getFont().getTextSize()
//                + " ,colour: " + content.getColour().getColour()
//                + " ,font: " + content.getFont().getFont()
//                + " , TEXT: " + content.getContent());
//    }
}
