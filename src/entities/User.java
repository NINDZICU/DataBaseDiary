package entities;

import DAO.Identified;

/**
 * Created by Admin on 06.12.2016.
 */
public class User implements Identified<Integer> {
    private int id;
    private String user_name;
    private String user_login;
    private String user_password;

    public User(String user_name, String user_login, String user_password) {
        this.user_name = user_name;
        this.user_login = user_login;
        this.user_password = user_password;
    }

    public User(int id, String user_name) {
        this.user_name = user_name;
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_login() {
        return user_login;
    }

    public String getUser_password() {
        return user_password;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
