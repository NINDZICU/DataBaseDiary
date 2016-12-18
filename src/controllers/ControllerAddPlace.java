package controllers;

import DB.DBWrapper;
import DataWithMsSql.PlaceDataWithMsSql;
import entities.Place;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.sql.SQLException;


/**
 * Created by Admin on 18.12.2016.
 */
public class ControllerAddPlace {
    @FXML
    private Button btnOk;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private TextField tfName;

    @FXML
    private void initialize() {

//        btnOk.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
////                if(!tfName.getText().equals("")){
////                    try {
////                        PlaceDataWithMsSql placeData = new PlaceDataWithMsSql(DBWrapper.getConnection());
////                        Place place = new Place(tfName.getText(), textAreaDescription.getText());
////                        placeData.create(place);
////                        placeData.getLastId("Places");
////                    } catch (SQLException e) {
////                        e.printStackTrace();
////                    }
////                    Stage stage = (Stage) btnOk.getScene().getWindow();
////                    stage.close();
////                }
////            }
//        });
    }
}
