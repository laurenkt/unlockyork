package models;
public class Transitions {

    String startTrigger;

    int start;
    int duration;

    public Transitions(String startTrigger, int start, int duration) {
        this.startTrigger = startTrigger;
        this.start = start;
        this.duration = duration;
    }
}
