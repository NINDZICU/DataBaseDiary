package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 18.12.2016.
 */
public abstract class AbstractDao<T extends Identified<PK>, PK extends Integer> implements GenericDAO<T, PK> {
    private Connection connection;

    public AbstractDao(Connection connection) {
        this.connection = connection;
    }

    public abstract String getSelectQuery();


    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();

    protected abstract List<T> getResultSet(ResultSet rs) throws SQLException;

    /**
     * Устанавливает аргументы insert запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    /**
     * Устанавливает аргументы update запроса в соответствии со значением полей объекта object.
     */
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;


    @Override
    public void createEntity(T object) throws SQLException {
        String sql = getCreateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            prepareStatementForInsert(statement, object);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public int getLastId(String nameTable) throws SQLException {
        int id = 0;
        String sql = "EXECUTE getLastId @nameTable='" + nameTable + "'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            rs.next();
            System.out.println(rs.getInt("id"));
            id= rs.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public void update(T object) throws SQLException {
        String sql = getUpdateQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            System.out.println(sql);
            prepareStatementForUpdate(statement, object);
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(T object) throws SQLException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement.executeUpdate();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<T> getAll() throws SQLException {
        List<T> list = null;
        String sql = getSelectQuery();
        System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();

            list = getResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<T> getAllFromEdit(String sql) throws SQLException {
        List<T> list = null;
        System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = getResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
