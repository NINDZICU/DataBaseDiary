package DataWithMsSql;

import DAO.AbstractDao;
import entities.FriendGroup;
import model.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 18.12.2016.
 */
public class FriendsGroupWitnMsSql extends AbstractDao<FriendGroup,Integer> {
    String date;

    public void setDate(String date) {
        this.date = date;
    }

    public FriendsGroupWitnMsSql(Connection connection) {
        super(connection);
    }

    @Override
    public void create(FriendGroup object) throws SQLException {
        createEntity(object);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT event_id,friend_id FROM friends_group;";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO friends_group (event_id, friend_id)"+
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM friends_group WHERE id= ?;";

    }

    @Override
    protected List<FriendGroup> getResultSet(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, FriendGroup object) throws SQLException {
        try {

            statement.setInt(1, object.getEvent_id());
            statement.setInt(2, object.getFriend_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, FriendGroup object) throws SQLException {

    }
}
