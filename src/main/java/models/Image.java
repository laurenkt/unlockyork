package models;

public class Image implements SlideElement {

    String path; //file path for the image file

    PositionAttrib positionAttrib; //positionAttrib image will appear on a slide

    Image(String path, PositionAttrib positionAttrib) {
        this.path = path;
        this.positionAttrib = positionAttrib;
    }

    public Image()
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
