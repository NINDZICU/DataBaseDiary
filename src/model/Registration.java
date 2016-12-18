package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Admin on 10.12.2016.
 */
public class Registration {

    public Registration() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registration.fxml"));
        GridPane load = (GridPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Registration");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }
}
