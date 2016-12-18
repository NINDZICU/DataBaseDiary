package controllers;

import DataWithMsSql.EventDataWithMsSql;
import DB.DBWrapper;
import DataWithMsSql.PlaceDataWithMsSql;
import entities.Event;
import entities.Friend;
import entities.Place;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Edit;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static java.awt.event.MouseEvent.BUTTON1;


/**
 * Created by Admin on 05.12.2016.
 */
public class ControllerMain {
    private ControllerEdit children;  // Ссылка на контроллер поражаемой формы
    private ObservableList<Event> notes = FXCollections.observableArrayList();
    @FXML
    private TableView<Event> tableNotes;
    @FXML
    private TableColumn columnNote;
    @FXML
    private TableColumn columnForTime;
    @FXML
    private TableColumn columnToTime;
    @FXML
    private TableColumn placeColumn;
    @FXML
    private TableColumn friendColumn;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button addNote;
    @FXML
    private Button next;
    @FXML
    private Button previous;
    private EventDataWithMsSql eventData;


    @FXML
    private void initialize() throws SQLException {
        /**
         * обработка нажатия по таблице->переход в update
         */
        tableNotes.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton()==MouseButton.PRIMARY) {
                    System.out.println(tableNotes.getSelectionModel().getSelectedCells());
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editPage.fxml"));
                    try {
//                    Parent newView = loader.load(getClass().getResource("/views/editPage.fxml"));  // получение главного контейнера создаваемой формы
                        GridPane load = (GridPane) loader.load();
                        Stage stage = new Stage();
                        stage.setTitle("Registration");
                        Scene scene = new Scene(load);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    children = loader.getController();  // Теперь текущий контроллер "знает" о существовании "потомка"
                    try {
                        children.update(notes.get(tableNotes.getSelectionModel().getSelectedIndex()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    /**
                     * на правой кнопке удаление из таблицы
                     */
                    children.setParent(ControllerMain.this);
                } else if(event.getButton()== MouseButton.SECONDARY){
                    Event event1 = notes.get(tableNotes.getSelectionModel().getSelectedIndex());
                    try {
                        EventDataWithMsSql eventdata = new EventDataWithMsSql(DBWrapper.getConnection());
                        eventdata.delete(event1);
                        notes.clear();
                        eventData = new EventDataWithMsSql(DBWrapper.getConnection());
                        List<Event>events = eventData.getAll();
                        for (int i = 0; i < events.size(); i++) {
                            notes.add(events.get(i));
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            }

        });


        datePicker.setValue(LocalDate.now());
        columnNote.setCellValueFactory(new PropertyValueFactory<Event, String>("event_name"));
        columnForTime.setCellValueFactory(new PropertyValueFactory<Event, String>("event_begin_time"));
        columnToTime.setCellValueFactory(new PropertyValueFactory<Event, String>("event_end_time"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<Place, String>("place_name"));
        friendColumn.setCellValueFactory(new PropertyValueFactory<Friend, String>("friend_name"));

        getTableData();


        addNote.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    new Edit();
                    Stage stage = (Stage) addNote.getScene().getWindow();
                    stage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        next.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LocalDate date = datePicker.getValue();
                datePicker.setValue(date.plusDays(1));
                getTableData();
            }
        });
        previous.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                LocalDate date = datePicker.getValue();
                datePicker.setValue(date.minusDays(1));

                getTableData();
            }
        });
        datePicker.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                getTableData();
            }
        });
    }

    public void getTableData() {
        notes.clear();
        List<Event> events = null;
        List<Place> places = null;
        try {
//            if (eventData == null) {
                eventData = new EventDataWithMsSql(DBWrapper.getConnection());
//            }
            PlaceDataWithMsSql placeData = new PlaceDataWithMsSql(DBWrapper.getConnection());
            eventData.setDate(String.valueOf(datePicker.getValue()));
            placeData.setDate(String.valueOf(datePicker.getValue()));
            events = eventData.getAll();
            places = placeData.getAll();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < events.size(); i++) {
            notes.add(events.get(i));
        }
        for (int i = 0; i < places.size(); i++) {
//            places.add(places.get(i));

        }
        tableNotes.setItems(notes);

    }


}
