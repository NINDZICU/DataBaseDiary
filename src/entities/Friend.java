package entities;

import DAO.Identified;

/**
 * Created by Admin on 17.12.2016.
 */
public class Friend implements Identified<Integer> {
    private int id;
    private String friend_name;

    public Friend(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
