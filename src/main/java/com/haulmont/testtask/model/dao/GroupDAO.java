package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupDAO<E extends Entity, T extends Group> implements DAO<E, T> {

    @Override
    public List<T> select(E e) {
        return null;
    }

    @Override
    public List<T> selectAll() {
        return null;
    }

    @Override
    public boolean delete(E e) {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("DELETE FROM " +
                "GROUPS WHERE ID = ?;")){

            preparedStatement.setLong(1, e.getId());
            return preparedStatement.execute();
        } catch (CriticalException e1) {


            e1.printStackTrace();

            //return false;
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return false;
    }

    @Override
    public T update(T t) {
        return null;
    }


    @Override
    public List<T> insert(List<T> list) {
        return null;
    }
}
