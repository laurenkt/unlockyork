package models;

import java.util.ArrayList;

public class Text {

    Position position;

    ArrayList<textContent> content;

    Text(Position position, ArrayList<textContent> content) {
        this.position = position;
        this.content = content;
    }

    public Text()
    {
        position = new Position();
        content = new ArrayList<textContent>();
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
}
