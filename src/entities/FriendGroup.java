package entities;

import DAO.Identified;

/**
 * Created by Admin on 18.12.2016.
 */
public class FriendGroup implements Identified<Integer> {
    private int event_id;
    private int friend_id;

    public FriendGroup(int event_id, int friend_id) {
        this.event_id = event_id;
        this.friend_id = friend_id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    @Override
    public Integer getId() {
        return null;
    }
}
