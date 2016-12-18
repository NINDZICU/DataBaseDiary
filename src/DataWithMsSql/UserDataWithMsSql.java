package DataWithMsSql;

import DAO.AbstractDao;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 18.12.2016.
 */
public class UserDataWithMsSql extends AbstractDao<User, Integer> {
    private String login;
    private String password;

    public UserDataWithMsSql(Connection connection) {
        super(connection);
    }

    @Override
    public void create(User object) throws SQLException {
        createEntity(object);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT users_id,user_name FROM Users WHERE user_login= '"+login+"' AND user_password='"+password+"';";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO Users(user_name,user_login,user_password) VALUES (?,?,?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE Users SET user_name=?, user_password=? WHERE users_id=?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM Users WHERE id= ?;";

    }

    @Override
    protected List<User> getResultSet(ResultSet rs) throws SQLException {
        ArrayList<User> events = new ArrayList<>();
        try {
            while (rs.next()) {
                events.add(new User(
                        rs.getInt("users_id"),
                        rs.getString("user_name")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, User object) throws SQLException {
        try {
            statement.setString(1, object.getUser_name());
            statement.setString(2, object.getUser_login());
            statement.setString(3, object.getUser_password());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, User object) throws SQLException {
        try {
            statement.setString(1, object.getUser_name());
            statement.setString(2, object.getUser_password());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
