package controllers;

import DataHandlers.CheckData;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MainPage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Created by Admin on 09.12.2016.
 */
public class ControllerRegistration {
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfConfirm;
    @FXML
    private Label errName;
    @FXML
    private Label errLogin;
    @FXML
    private Label errPassword;
    @FXML
    private Label errConfirm;
    @FXML
    private Button btnRegistr;

    @FXML
    public void initialize() {
        final CheckData checkData = new CheckData();

        btnRegistr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    if(checkData.addUser(ControllerRegistration.this)){
                        new MainPage();
                        Stage stage = (Stage) btnRegistr.getScene().getWindow();
                        stage.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public TextField getTfName() {
        return tfName;
    }

    public TextField getTfLogin() {
        return tfLogin;
    }

    public PasswordField getTfPassword() {
        return tfPassword;
    }

    public PasswordField getTfConfirm() {
        return tfConfirm;
    }

    public void setErrName(String errName) {
        this.errName.setText(errName);
    }

    public void setErrLogin(String errLogin) {
        this.errLogin.setText(errLogin);
    }

    public void setErrPassword(String errPassword) {
        this.errPassword.setText(errPassword);
    }

    public void setErrConfirm(String errConfirm) {
        this.errConfirm.setText(errConfirm);
    }
}
