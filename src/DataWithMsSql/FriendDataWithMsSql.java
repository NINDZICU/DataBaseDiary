package DataWithMsSql;

import DAO.AbstractDao;
import entities.Friend;
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
public class FriendDataWithMsSql extends AbstractDao<Friend, Integer> {
    String date;

    public void setDate(String date) {
        this.date = date;
    }

    public FriendDataWithMsSql(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Friend object) throws SQLException {
        createEntity(object);
    }

    @Override
    public String getSelectQuery() {
        return "EXECUTE get_friends @UserId="+Main.USERID+" , @date='"+date+"'";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO Friends (friend_name) " +
                "VALUES (?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE Friends SET friend_name=? WHERE friend_id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM Friends WHERE friend_id= ?;";

    }

    @Override
    protected List<Friend> getResultSet(ResultSet rs) throws SQLException {
        ArrayList<Friend> friends = new ArrayList<>();
        try {
            while (rs.next()) {
                friends.add(new Friend(
                        rs.getString("friend_name")

                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return friends;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Friend object) throws SQLException {
        try {

            statement.setString(1, object.getFriend_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Friend object) throws SQLException {
        try {
            statement.setString(1, object.getFriend_name());
            statement.setInt(2, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
