package models;

public class Audio {

    String path;

    Position position;

    Audio(String path, Position position) {
        this.path = path;
        this.position = position;
    }

    public Audio()
    {
        path = "null";
        position = new Position();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
