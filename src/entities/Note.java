package entities;

/**
 * Created by Admin on 06.12.2016.
 */
public class Note {
    private String text;
    private String time;
    private String toTime;

    public Note(String text, String time, String toTime) {
        this.text = text;
        this.time = time;
        this.toTime=toTime;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getToTime() {
        return toTime;
    }
}
