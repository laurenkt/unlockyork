package models;

public class Image {

    String path; //file path for the image file

    Position position; //position image will appear on a slide

    Image(String path, Position position) {
        this.path = path;
        this.position = position;
    }

    public Image()
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
