package DataWithMsSql;

import DAO.AbstractDao;
import entities.Place;
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
public class PlaceDataWithMsSql extends AbstractDao<Place,Integer>{
    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public PlaceDataWithMsSql(Connection connection) {
        super(connection);
    }

    @Override
    public void create(Place object) throws SQLException {
        createEntity(object);
    }

    @Override
    public String getSelectQuery() {
        return "EXECUTE get_notes_today @UserId="+ Main.USERID+" , @date='"+date+"'";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO Places (place_name, description) " +
                "VALUES (?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE Places SET place_name=?, description=? WHERE place_id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM Places WHERE id= ?;";

    }

    @Override
    protected List<Place> getResultSet(ResultSet rs) throws SQLException {
        ArrayList<Place> places = new ArrayList<>();
        try {
            while (rs.next()) {
                places.add(new Place(
                        rs.getString("place_name"),
                        rs.getString("description")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Place object) throws SQLException {
        try {

            statement.setString(1, object.getPlace_name());
            statement.setString(2, object.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Place object) throws SQLException {
        try {
            statement.setString(1, object.getPlace_name());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
