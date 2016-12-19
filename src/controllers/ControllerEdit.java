package controllers;

import DataWithMsSql.*;
import DB.DBWrapper;
import DB.ORM;
import entities.*;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import FXelements.TimeSpinner;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.AddPlace;
import model.MainPage;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12.12.2016.
 */
public class ControllerEdit {
    private String NOW = "Во время события";
    private final static String FIVE = "За 5 минут";
    private final static String TEN = "За 10 минут";
    private final static String FIFTEEN = "За 15 минут";
    private final static String HOUR = "За 1 час";
    private final static String DAY = "За 1 день";
    private final static String TWODAY = "за 2 дня";
    private final static String WEEK = "за неделю";
    private LocalDate reminderDate;
    private LocalTime reminderTime;
    private ArrayList<Friend> friends;
    private FriendDataWithMsSql friendData;
    private ReminderDataWithMsSql reminderData;
    private ControllerMain parent;
    private Event event;
    private boolean edit = false;

    @FXML
    private Label errEvent;
    @FXML
    private TextField tfNameEvent = new TextField();
    @FXML
    private DatePicker datePicker;
    @FXML
    private TimeSpinner fromTimeSpinner = new TimeSpinner();
    @FXML
    private TimeSpinner toTimeSpinner = new TimeSpinner();
    @FXML
    private Button btnSave;
    @FXML
    private ChoiceBox choiceReminder;
    @FXML
    private Label labFriend;
    @FXML
    private Button btnAddFriend;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private TextField tfFriend;
    @FXML
    private TextField tfName;

    @FXML
    public void initialize() {
        tfName.setText(" ");
        datePicker.setValue(LocalDate.now());
        tfFriend.setText(" ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        fromTimeSpinner.valueProperty().addListener((obs, oldTime, newTime) ->
//                System.out.println(formatter.format(newTime)));
        choiceReminder.setItems(FXCollections.observableArrayList (
                NOW, FIVE, TEN, FIFTEEN, HOUR, DAY, TWODAY, WEEK));
        choiceReminder.getSelectionModel().selectFirst();
        friends = new ArrayList<>();


        btnSave.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String text = tfNameEvent.getText();
                errEvent.setText("");
                boolean check = false;
                try {
                    EventDataWithMsSql eventData = new EventDataWithMsSql(DBWrapper.getConnection());
                    eventData.setDate(String.valueOf(datePicker.getValue()));
                    ArrayList<Event> events = (ArrayList<Event>) eventData.getAll();
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:MM");
//                    for (int i = 0; i < events.size(); i++) {
//                        LocalTime localTime = LocalTime.parse(events.get(i).getEvent_begin_time(), formatter1);
//                        LocalTime localTime2 = LocalTime.parse(events.get(i).getEvent_end_time(), formatter1);
//                        if ((localTime.getHour() > toTimeSpinner.getValue().getHour() && localTime2.getHour() > toTimeSpinner.getValue().getHour()
//                                 || (
//                                localTime.getHour() < fromTimeSpinner.getValue().getHour() && localTime2.getHour() < fromTimeSpinner.getValue().getHour()))) {
//                            check = true;
//                        }else if((localTime.getHour() ==fromTimeSpinner.getValue().getHour()||localTime2.getHour()==fromTimeSpinner.getValue().getHour())&&(localTime.getMinute() < fromTimeSpinner.getValue().getMinute() &&
//                                localTime2.getMinute() < fromTimeSpinner.getValue().getMinute())||(localTime.getMinute() > toTimeSpinner.getValue().getMinute() &&
//                                localTime2.getMinute() > toTimeSpinner.getValue().getMinute())){
//                                check =true;
//                        }
//                        else {
//                            check = false;
//                            errEvent.setText("Выбранное время пересекается с уже существующими записями");
//                            break;
//                        }
//                    }

                    if (!text.equals("")) {
                        ORM orm = new ORM();
                        Event event1 = new Event(
                                text,
                                String.valueOf(datePicker.getValue()),
                                String.valueOf(formatter.format(fromTimeSpinner.getValue())),
                                String.valueOf(formatter.format(toTimeSpinner.getValue()))
                        );
                        getLengthTime();
                        Reminder reminder = new Reminder(
                                reminderDate.toString(), reminderTime.toString()
                        );
                        reminderData = new ReminderDataWithMsSql(DBWrapper.getConnection());
                        if(edit) reminderData.update(reminder);
                       else {
                            reminderData.create(reminder);
                            reminder.setId(reminderData.getLastId("Reminders"));
                        }

                        if (!tfName.getText().equals("")) {
                            try {
                                PlaceDataWithMsSql placeData = new PlaceDataWithMsSql(DBWrapper.getConnection());
                                Place place = new Place(tfName.getText(), textAreaDescription.getText());
                                if(edit) placeData.update(place);
                                else {
                                    placeData.create(place);
                                    int placeId = placeData.getLastId("Places");
                                    event1.setPlace_id(placeId);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                        eventData = new EventDataWithMsSql(DBWrapper.getConnection());
                       if(edit) eventData.update(event1);
                       else {
                           event1.setReminder_id(reminder.getId());
                           eventData.create(event1);
                           event1.setId(eventData.getLastId("Events"));

                           if (friends.size() > 0) {
                               FriendsGroupWitnMsSql friendsGroupData = new FriendsGroupWitnMsSql(DBWrapper.getConnection());
                               for (int i = 0; i < friends.size(); i++) {
                                   System.out.println(friends.get(i).getId());
                                   friendsGroupData.create(new FriendGroup(event1.getId(),
                                           friends.get(i).getId()
                                   ));
                               }
                           }
                       }
//                        orm.insert(Event.class, event1);
                        new MainPage();
                       edit=false;
                        Stage stage = (Stage) btnSave.getScene().getWindow();
                        stage.close();
                    } else if (text.equals("")) errEvent.setText("Введите название мероприятия!");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        btnAddFriend.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!tfFriend.getText().equals("")) {
                    Friend friend = new Friend(tfFriend.getText());
                    try {
                        if (friendData == null)
                            friendData = new FriendDataWithMsSql(DBWrapper.getConnection());
                        if(edit)friendData.update(friend);
                        else {
                            friendData.create(friend);
                            int friendId = friendData.getLastId("Friends");
                            friend.setId(friendId);
                        }
                        friends.add(friend);
                        labFriend.setText(labFriend.getText() + " " + friend.getFriend_name());
                        tfFriend.setText("");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getLengthTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd:mm HH:mm:ss");
        LocalDateTime date;
//        formatter.format(date);

        try {
            if (choiceReminder.getValue().equals(NOW)) {
                reminderDate = datePicker.getValue();
                reminderTime = fromTimeSpinner.getValue();
            } else if (choiceReminder.getValue().equals(FIVE)) {
                reminderTime = fromTimeSpinner.getValue().minusMinutes(5);
                if (reminderTime.getHour() > fromTimeSpinner.getValue().getHour()) {
                    reminderDate = datePicker.getValue().minusDays(1);
                } else reminderDate = datePicker.getValue();
            } else if (choiceReminder.getValue().equals(TEN)) {
                reminderTime = fromTimeSpinner.getValue().minusMinutes(10);
                if (reminderTime.getHour() > fromTimeSpinner.getValue().getHour()) {
                    reminderDate = datePicker.getValue().minusDays(1);
                } else reminderDate = datePicker.getValue();
            } else if (choiceReminder.getValue().equals(FIFTEEN)) {
                reminderTime = fromTimeSpinner.getValue().minusMinutes(15);
                if (reminderTime.getHour() > fromTimeSpinner.getValue().getHour()) {
                    reminderDate = datePicker.getValue().minusDays(1);
                } else reminderDate = datePicker.getValue();
            } else if (choiceReminder.getValue().equals(HOUR)) {
                reminderTime = fromTimeSpinner.getValue().minusHours(1);
                if (reminderTime.getHour() > fromTimeSpinner.getValue().getHour()) {
                    reminderDate = datePicker.getValue().minusDays(1);
                } else reminderDate = datePicker.getValue();

                System.out.println(reminderTime);
            } else if (choiceReminder.getValue().equals(DAY)) {
                reminderDate = datePicker.getValue().minusDays(1);
                reminderTime = fromTimeSpinner.getValue();
                System.out.println(reminderTime);
            } else if (choiceReminder.getValue().equals(TWODAY)) {
                reminderDate = datePicker.getValue().minusDays(2);
                reminderTime = fromTimeSpinner.getValue();
                System.out.println(reminderTime);
            } else if (choiceReminder.getValue().equals(WEEK)) {
                reminderDate = datePicker.getValue().minusWeeks(1);
                reminderTime = fromTimeSpinner.getValue();
                System.out.println(reminderTime);
            }
        } catch (NullPointerException e) {
            reminderDate = datePicker.getValue();
            reminderTime = fromTimeSpinner.getValue();
        }
    }


    public void setParent(ControllerMain parent) {
        this.parent = parent;
    }
    public void update(Event event) throws SQLException {
        edit=true;
        this.event =event;
        tfNameEvent.setText(event.getEvent_name());

        datePicker.setValue(LocalDate.parse(event.getEvent_date()));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
//        LocalDate dateLocal = LocalDate.parse(event.getEvent_begin_time(), formatter);
        EventDataWithMsSql eventData = new EventDataWithMsSql(DBWrapper.getConnection());
        String sql = eventData.getAllFromEdit(event.getId());
        FriendDataWithMsSql friendData = new FriendDataWithMsSql(DBWrapper.getConnection());
        PlaceDataWithMsSql placeData = new PlaceDataWithMsSql(DBWrapper.getConnection());
        List<Friend> friends= friendData.getAllFromEdit(sql);
        List<Place> places= placeData.getAllFromEdit(sql);
        textAreaDescription.setText(places.get(0).getDescription());
        tfName.setText(places.get(0).getPlace_name());
        for (int i = 0; i < friends.size(); i++) {
            labFriend.setText(labFriend.getText()+friends.get(i).getFriend_name()+" ");
        }

//        tfFriend.setText(friends.get(0).getFriend_name());


    }

//    public String getText() {
//        return text;
//    }
}
