package models;

public class Meta{

    String key;
    String value;


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
