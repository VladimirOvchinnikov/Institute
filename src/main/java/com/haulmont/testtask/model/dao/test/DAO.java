package com.haulmont.testtask.model.dao.test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface DAO<E extends Entity, T> {

    default public List<T> select(List<E> entities){
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
                    T entity = getEntity(rs);
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

    public String sqlSelectBuilder(List<E> entities);
    public T getEntity(ResultSet rs) throws SQLException;

    default public int delete(List<E> entities){
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

    public String sqlDeleteBuilder(List<E> entities);

    default public boolean update(T t){
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getUpdateSql())) {
            setParametersUpdate(preparedStatement, t);
//            preparedStatement.setInt(1, t.getNumber());
//            preparedStatement.setString(2, t.getFaculty());
//            preparedStatement.setLong(3, t.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CriticalException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUpdateSql();
    public void setParametersUpdate(PreparedStatement ps, T t) throws SQLException;

    default public Long insert(T obj){
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getInsertSql(), Statement.RETURN_GENERATED_KEYS)) {
            setParametersInsert(preparedStatement, obj);

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

    public String getInsertSql();
    public void setParametersInsert(PreparedStatement ps, T t) throws SQLException;
}
