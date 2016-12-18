package DataWithMsSql;

import DAO.AbstractDao;
import entities.Reminder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 18.12.2016.
 */
public class ReminderDataWithMsSql extends AbstractDao<Reminder, Integer> {
    public ReminderDataWithMsSql(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Reminder object) throws SQLException {
        createEntity(object);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT reminder_date,reminder_time FROM Reminders;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO Reminders (reminder_date, reminder_time) " +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE Reminders SET reminder_date=?, reminder_time=? WHERE reminder_id=?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM Reminders WHERE id= ?;";

    }

    @Override
    protected List<Reminder> getResultSet(ResultSet rs) throws SQLException {
        ArrayList<Reminder> events = new ArrayList<>();
        try {
            while (rs.next()) {
                events.add(new Reminder(
                        rs.getString("reminder_date"),
                        rs.getString("reminder_time")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

        @Override
        protected void prepareStatementForInsert (PreparedStatement statement, Reminder object) throws SQLException {
            try {
                statement.setString(1, object.getReminder_date());
                statement.setString(2, object.getReminder_time());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void prepareStatementForUpdate (PreparedStatement statement, Reminder object) throws SQLException {
            try {
                statement.setString(1, object.getReminder_date());
                statement.setString(2, object.getReminder_time());
                statement.setInt(3, object.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
