package models;
public class Transitions {

    String startTrigger;

    int start;
    int duration;

    Transitions(String startTrigger, int start, int duration) {
        this.startTrigger = startTrigger;
        this.start = start;
        this.duration = duration;
    }

    public Transitions()
    {
        startTrigger = "null";

        start = 0;
        duration = 0;
    }

    public String getStartTrigger() {
        return startTrigger;
    }

    public void setStartTrigger(String startTrigger) {
        this.startTrigger = startTrigger;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
