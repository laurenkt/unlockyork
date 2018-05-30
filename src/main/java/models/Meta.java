package models;

public class Meta implements SlideElement {

    String key; //what the value means ie.author
    String value; //value of key ie. authors name


    Meta(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Meta()
    {
        key = "null";
        value = "null";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
