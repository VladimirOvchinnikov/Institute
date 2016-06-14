package com.haulmont.testtask.model.dao.test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupDAO<E extends Entity, T extends Group> implements DAO<E, T> {

    @Override
    public List<T> select(List<E> ids) {
        List<T> result = Lists.newArrayList();
        StringBuilder sql = new StringBuilder("SELECT * FROM GROUPS");
        if (ids.size() > 0) {
            sql.append(" WHERE ID IN (");
            for (int i = 0; i < ids.size() - 1; i++) {
                sql.append("?, ");
            }
            sql.append("?)");
        }
        sql.append(";");
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sql.toString())) {

            int i = 1;
            for (E e : ids) {
                preparedStatement.setLong(i, e.getId());
                i++;
            }
            preparedStatement.execute();
            try (ResultSet rs = preparedStatement.getResultSet()) {
                while (rs.next()) {
                    T group = (T) new Group();
                    group.setId(rs.getLong("id"));
                    group.setNumber(rs.getInt("number"));
                    group.setFaculty(rs.getString("faculty"));
                    result.add(group);
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

    @Override
    public int delete(List<E> entitys) {
        if (entitys.size() == 0) {
            return 0;
        }

        StringBuilder sql = new StringBuilder("DELETE FROM GROUPS WHERE ID in (");
        for (int i = 0; i < entitys.size() - 1; i++) {
            sql.append("?, ");
        }
        sql.append("?);");
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sql.toString())) {
            List<Long> ids = entitys.stream().map(e -> e.getId()).collect(Collectors.toList());
            for (int i = 1; i < entitys.size() + 1; i++) {
                preparedStatement.setLong(i, entitys.get(i - 1).getId());
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

    @Override
    public boolean update(T t) {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("UPDATE GROUPS SET NUMBER = ? , FACULTY = ? WHERE ID = ?")) {
            preparedStatement.setInt(1, t.getNumber());
            preparedStatement.setString(2, t.getFaculty());
            preparedStatement.setLong(3, t.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CriticalException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public Long insert(T obj) {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("INSERT INTO GROUPS (number, faculty) values(?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, obj.getNumber());
            preparedStatement.setString(2, obj.getFaculty());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return -1L;
            }
            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                obj.setId(rs.getLong(1));
            }
            return obj.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1L;
        } catch (CriticalException e) {
            e.printStackTrace();
            return -1L;
        }
    }
}
