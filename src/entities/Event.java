package entities;

import DAO.Identified;

/**
 * Created by Admin on 18.12.2016.
 */
public class Event implements Identified<Integer> {
    private int id;
    private int reminder_id;
    private int place_id;
    private int users_id;
    private String event_name;
    private String event_date;
    private String event_begin_time;
    private String event_end_time;

    public Event(int reminder_id, int place_id, int users_id, String event_name, String event_date, String event_begin_time, String event_end_time) {
        this.reminder_id = reminder_id;
        this.place_id = place_id;
        this.users_id = users_id;
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_begin_time = event_begin_time;
        this.event_end_time = event_end_time;
    }

    public Event(String event_name, String event_date, String event_begin_time, String event_end_time) {
        this.event_name = event_name;
        this.event_date = event_date;
        this.event_begin_time = event_begin_time;
        this.event_end_time = event_end_time;
    }

    public Event(int id, String event_name,String event_date, String event_begin_time, String event_end_time) {
        this.id = id;
        this.event_name = event_name;
        this.event_date=event_date;
        this.event_begin_time = event_begin_time;
        this.event_end_time = event_end_time;
    }

    public int getReminder_id() {
        return reminder_id;
    }

    public int getPlace_id() {
        return place_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_date() {
        return event_date;
    }

    public String getEvent_begin_time() {
        return event_begin_time;
    }

    public String getEvent_end_time() {
        return event_end_time;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReminder_id(int reminder_id) {
        this.reminder_id = reminder_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }
}
