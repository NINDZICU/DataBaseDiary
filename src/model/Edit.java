package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Admin on 12.12.2016.
 */
public class Edit {
    public Edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editPage.fxml"));
        GridPane load = (GridPane) loader.load();
        Stage stage = new Stage();
        stage.setTitle("Registration");
        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }
}
