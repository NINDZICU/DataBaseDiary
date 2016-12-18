package entities;

import DAO.Identified;

/**
 * Created by Admin on 17.12.2016.
 */
public class Reminder implements Identified<Integer> {
    private int id;
    private String reminder_date;
    private String reminder_time;

    public Reminder(String reminder_date, String reminder_time) {
        this.reminder_date = reminder_date;
        this.reminder_time = reminder_time;
    }

    public String getReminder_date() {
        return reminder_date;
    }

    public String getReminder_time() {
        return reminder_time;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
