package DataHandlers;

import DataWithMsSql.UserDataWithMsSql;
import DB.DBWrapper;
import DB.UserDataBase;
import controllers.ControllerRegistration;
import controllers.ControllerSignUp;
import entities.User;
import model.Main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Admin on 17.12.2016.
 */
public class CheckData {
    private ControllerRegistration controllerRegistration;
    private UserDataWithMsSql userData;
    private UserDataBase db = new UserDataBase();

    private String name;
    private String login;
    private String password;
    private String repassword;

    public boolean addUser(ControllerRegistration controllerRegistration) throws IOException, SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        this.controllerRegistration = controllerRegistration;

        name = controllerRegistration.getTfName().getText();
        login = controllerRegistration.getTfLogin().getText();
        password = controllerRegistration.getTfPassword().getText();
        repassword = controllerRegistration.getTfConfirm().getText();

        getEmptyErrorForms();

        if (checkDataForRegistration()) {
           UserDataWithMsSql userData= new UserDataWithMsSql(DBWrapper.getConnection());
           userData.create(new User(name, login, password));
           Main.USERID=userData.getLastId("Users");
//
// ORM orm = new ORM();
//            orm.insert(User.class, new User(name, login, password));
            return true;
        } else return false;
    }


    public boolean checkDataForRegistration() throws IOException, SQLException {
        boolean check = true;

        if (name.equals("")) {
            check = false;
            controllerRegistration.setErrName(" Enter the name <br>");
        } else if (!name.matches("^[a-zA-Z\\s{,2}]{0,50}$")) {
            check = false;
            controllerRegistration.setErrName("Name must contain valid characters, latin characters");
        } else if (db.checkLogin(login)) {
            check = false;
            controllerRegistration.setErrLogin(" This login already taken <br>");
        }
        if (!login.matches("^[a-zA-Z0-9_.-]{4,80}$")) {
            check = false;
            controllerRegistration.setErrLogin(" Login must contain valid characters and have a length from 4 to 80<br>");
        }
        if (password.length() < 6) {
            check = false;
            controllerRegistration.setErrPassword(" The password must be at least 6 characters <br>");
        }
        if (!password.equals(repassword)) {
            check = false;
            controllerRegistration.setErrConfirm(" Passwords don't match");
        }

        return check;
    }

    public void getEmptyErrorForms() {
        controllerRegistration.setErrConfirm("");
        controllerRegistration.setErrLogin("");
        controllerRegistration.setErrName("");
        controllerRegistration.setErrPassword("");
    }

    public boolean checkDataForAuttentification(ControllerSignUp controllerSignUp) {
        controllerSignUp.setErrSignUp("");
        try {
            if (userData == null)
                userData = new UserDataWithMsSql(DBWrapper.getConnection());
            String login = controllerSignUp.getTfLogin().getText();
            String password = controllerSignUp.getPfPassword().getText();
            if (login.equals("")) {
                controllerSignUp.setErrSignUp("Введите логин");
                return false;
            }
            if (password.equals("")) {
                controllerSignUp.setErrSignUp("Введите пароль");
                return false;
            }
            userData.setLogin(login);
            userData.setPassword(password);
            ArrayList<User> users = (ArrayList<User>) userData.getAll();

            if (users.size() > 0) {
                Main.USERID = users.get(0).getId();
                return true;
            } else {
                controllerSignUp.setErrSignUp("Неверный логин или пароль!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
