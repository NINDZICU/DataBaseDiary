package DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Admin on 17.12.2016.
 */
public class UserDataBase {
    private static Connection conn;
    private static Statement stmt;
    private static ResultSet rs;


    public boolean checkLogin(String login) throws IOException, SQLException {
        conn = DBWrapper.getConnection();
        stmt = conn.createStatement();
        rs = stmt.executeQuery(
                "SELECT user_login FROM users WHERE user_login='" + login + "'");
        if (rs.next()) return true;
        else return false;
    }
}

