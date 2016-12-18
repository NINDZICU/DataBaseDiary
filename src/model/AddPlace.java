package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Admin on 18.12.2016.
 */
public class AddPlace {


    public AddPlace() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addPlace.fxml"));
        GridPane load = (GridPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Place");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }
}
