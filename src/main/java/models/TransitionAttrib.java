package models;
public class TransitionAttrib {

    String startTrigger; // if trigger, set by an event

    int duration; // time on screen

    TransitionAttrib(String startTrigger, int duration) {
        this.startTrigger = startTrigger;
        this.duration = duration;
    }

    public TransitionAttrib()
    {
        startTrigger = "null";
        duration = 0;
    }

    public String getStartTrigger() {
        return startTrigger;
    }

    public void setStartTrigger(String startTrigger) {
        this.startTrigger = startTrigger;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
