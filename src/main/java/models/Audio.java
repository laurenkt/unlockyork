package models;

public class Audio implements SlideElement, Positionable {

    String path; //file path to audio file

    PositionAttrib positionAttrib; //positionAttrib of audio on the slide

    Audio(String path, PositionAttrib positionAttrib) {
        this.path = path;
        this.positionAttrib = positionAttrib;
    }

    public Audio()
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
