package com.haulmont.testtask.model.dao;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.dao.helper.Helper;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface DAO<E extends Entity, T> extends SQLHelper<E, T> {

    default public List<T> select(List<E> entities) {
        List<T> result = Lists.newArrayList();
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sqlSelectBuilder(entities))) {

            int i = 1;
            for (E e : entities) {
                preparedStatement.setLong(i, e.getId());
                i++;
            }
            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getResultSet()) {
                while (rs.next()) {
                    T entity = (T) Helper.getEntity(Group.class, rs);
                    result.add(entity);
                }
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        } catch (CriticalException e) {
            e.printStackTrace();
            return result;
        }
    }

    default public int delete(List<E> entities) {
        if (entities.size() == 0) {
            return 0;
        }
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sqlDeleteBuilder(entities))) {
            List<Long> ids = entities.stream().map(e -> e.getId()).collect(Collectors.toList());
            for (int i = 1; i < entities.size() + 1; i++) {
                preparedStatement.setLong(i, entities.get(i - 1).getId());
            }
            return preparedStatement.executeUpdate();
        } catch (CriticalException e1) {
            e1.printStackTrace();
            return -1;
        } catch (SQLException e1) {
            e1.printStackTrace();
            return -1;
        }
    }

    default public boolean update(T t) {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getUpdateSql())) {
            setParametersUpdate(preparedStatement, t);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CriticalException e) {
            e.printStackTrace();
            return false;
        }
    }

    default public Long insert(T obj) {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS)) {
            Helper.setParametersInsert(preparedStatement, (Entity) obj);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return -1L;
            }
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        } catch (CriticalException e) {
            e.printStackTrace();
            return -1L;
        }
    }

}
