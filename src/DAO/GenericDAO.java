package DAO;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Admin on 18.12.2016.
 */
public interface GenericDAO<T extends Identified<PK>, PK extends Serializable>{

    public void create(T object) throws SQLException;

    public void createEntity(T object)throws SQLException;

    public void update(T object)throws SQLException;

    public void delete(T object)throws SQLException;

    public List<T> getAll()throws SQLException;

    public int getLastId(String nameTable) throws SQLException;

}
