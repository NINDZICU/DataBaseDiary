package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Admin on 17.12.2016.
 */
public class DBWrapper {
    private final static String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final static String CONNECTION_URI = "jdbc:sqlserver://localhost:1433;databaseName=diary_database";
    private final static String LOGIN = "admin";
    private final static String PASSWORD = "12345678";

    private static Connection conn;


    public static Connection getConnection() throws SQLException{
        if (conn == null) {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(CONNECTION_URI, LOGIN, PASSWORD);

            } catch (ClassNotFoundException e) {
                System.out.println("Can't find DB driver.");
            } catch (SQLException ex) {
                System.out.println("Can't connect to DB (" + ex.getErrorCode() + ": " + ex.getMessage() + ").");
            }

        }
        return conn;

    }
}
