package controllers;

import DB.DBWrapper;
import DB.ORM;
import DataHandlers.CheckData;
import DataWithMsSql.UserDataWithMsSql;
import entities.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.MainPage;
import model.Registration;

import java.io.IOException;
import java.sql.SQLException;

public class ControllerSignUp {
    @FXML
    private Button btnSignUp;
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfLogin;
    @FXML
    private Hyperlink linkRegistr;
    @FXML
    private Label errSignUp;

    @FXML
    public void initialize() {
        btnSignUp.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CheckData checkData = new CheckData();
                    if(checkData.checkDataForAuttentification(ControllerSignUp.this)) {
                        try {
                            new MainPage();
                            Stage stage = (Stage) btnSignUp.getScene().getWindow();
                            stage.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
        });
        linkRegistr.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new Registration();
                    Stage stage = (Stage) linkRegistr.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public PasswordField getPfPassword() {
        return pfPassword;
    }

    public TextField getTfLogin() {
        return tfLogin;
    }

    public void setErrSignUp(String text) {
         this.errSignUp.setText(text);
    }
}
