package models;

public class Video {

    String path; // path of the video file

    Position position; // position of video on slide

    Video(String path, Position position) {
        this.path = path;
        this.position = position;
    }

    public Video()
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
