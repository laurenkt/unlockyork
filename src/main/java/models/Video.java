package models;

public class Video implements SlideElement {

    String path; // path of the video file

    PositionAttrib positionAttrib; // positionAttrib of video on slide

    Video(String path, PositionAttrib positionAttrib) {
        this.path = path;
        this.positionAttrib = positionAttrib;
    }

    public Video()
    {
        path = "null";
        positionAttrib = new PositionAttrib();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public PositionAttrib getPosition() {
        return positionAttrib;
    }

    public void setPosition(PositionAttrib positionAttrib) {
        this.positionAttrib = positionAttrib;
    }
}
