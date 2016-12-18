package DataWithMsSql;

import DAO.AbstractDao;
import entities.Event;
import entities.Note;
import model.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 18.12.2016.
 */
public class EventDataWithMsSql extends AbstractDao<Event, Integer> {
    String date;

    public EventDataWithMsSql(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectQuery() {
        return "EXECUTE get_notes_today @UserId=" + Main.USERID + " , @date='" + date + "'";
//        return "SELECT event_id, event_name,event_begin_time,event_end_time FROM Events WHERE users_id='"+ Main.USERID+"' AND event_date='"+date+"';";
    }

    @Override
    public void create(Event event) throws SQLException {
        createEntity(event);
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO Events (event_name, event_date,event_begin_time,event_end_time,reminder_id,place_id,users_id) " +
                "VALUES (?, ?,?,?,?,?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE Events SET event_name=?, event_date=?, event_begin_time=?, event_end_time=? WHERE event_id= ?;";
//        return "UPDATE Events SET reminder_id= ? place_id = ? users_id=?" +
//                "event_name=? event_date=? event_begin_time=? event_end_time=? WHERE event_id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM Events WHERE event_id= ?;";

    }

    @Override
    protected List<Event> getResultSet(ResultSet rs) throws SQLException {
        ArrayList<Event> events = new ArrayList<>();
        try {
            while (rs.next()) {
                events.add(new Event(
                        rs.getInt("event_id"),
                        rs.getString("event_name"),
                        rs.getString("event_date"),
                        rs.getString("event_begin_time").substring(0, 5),
                        rs.getString("event_end_time").substring(0, 5)
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Event object) throws SQLException {
        try {

            statement.setString(1, object.getEvent_name());
            statement.setString(2, object.getEvent_date());
            statement.setString(3, object.getEvent_begin_time());
            statement.setString(4, object.getEvent_end_time());
            statement.setInt(5, object.getReminder_id());
            statement.setInt(6, object.getPlace_id());
            statement.setInt(7, Main.USERID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Event object) throws SQLException {
        try {
            statement.setString(1, object.getEvent_name());
            statement.setString(2, object.getEvent_date());
            statement.setString(3, object.getEvent_begin_time());
            statement.setString(4, object.getEvent_end_time());
            statement.setInt(5, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAllFromEdit(int event_id) {
        return "SELECT * FROM select_all WHERE event_id='" + event_id + "';";
    }
}
