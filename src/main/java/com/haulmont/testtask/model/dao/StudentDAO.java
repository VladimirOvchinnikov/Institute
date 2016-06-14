package com.haulmont.testtask.model.dao;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Student;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentDAO<E extends Entity, T extends Student> extends AbstractDAO<E,T> implements DAO<E, T> {

    public Map<Long, Integer> selectNumberGroupStudents(List<T> list) {//это для selectAll
        StringBuilder sql = new StringBuilder("SELECT STUDENTS.ID AS ID, GROUPS.NUMBER AS NUMBER FROM STUDENTS JOIN GROUPS ON STUDENTS.GROUP_ID = GROUPS.ID");
        Map<Long, Integer> map = Maps.newHashMap();
        if (list.size() > 0) {
            sql.append("WHERE STUDENTS.ID IN (");
            for (int i = 0; i < list.size() - 1; i++) {
                sql.append("?, ");
            }
            sql.append("?);");
        }
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sql.toString())) {
            int i = 1;
            for (T t : list) {
                preparedStatement.setLong(i, t.getId());
                i++;
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getLong("id"), rs.getInt("number"));
                }
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return map;
        } catch (CriticalException e) {
            e.printStackTrace();
            return map;
        }
    }

    public Map<Long, Integer> selectDistinctNumberGroup() {//это для выбора
        Map<Long, Integer> map = Maps.newHashMap();
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("SELECT ID, NUMBER FROM GROUPS")) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getLong("id"), rs.getInt("number"));
                }
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            return map;
        } catch (CriticalException e) {
            e.printStackTrace();
            return map;
        }
    }

    @Override
    protected String getTextQuerySelect(int size) {
        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENTS");
        if (size > 0) {
            sql.append(" WHERE ID IN (");
            for (int i = 0; i < size - 1; i++) {
                sql.append("?, ");
            }
            sql.append("?)");
        }
        sql.append(";");
        return sql.toString();
    }

    @Override
    protected T getEntity(ResultSet rs) throws SQLException {
        T student = (T) new Student();
        student.setId(rs.getLong("id"));
        student.setFirstName(rs.getString("first_name"));
        student.setMiddleName(rs.getString("middle_name"));
        student.setLastName(rs.getString("last_name"));
        student.setBirthDay(rs.getDate("birth_day"));
        student.setGroupId(rs.getLong("group_id"));
        return student;
    }

    @Override
    protected String getTextQueryDelete(int size) {
        StringBuilder sql = new StringBuilder("DELETE FROM STUDENTS WHERE ID in (");
        for (int i = 0; i < size - 1; i++) {
            sql.append("?, ");
        }
        sql.append("?);");
        return sql.toString();
    }

    @Override
    protected String getTextQueryUpdate() {
        return "SET FIRST_NAME = ? , MIDDLE_NAME = ?, LAST_NAME = ?, BIRTH_DAY = ?, GROUP_ID = ? WHERE ID = ?";
    }

    @Override
    protected void setParametersUpdate(PreparedStatement ps, T t) throws SQLException {
        setParametersInsert(ps,t);
        ps.setLong(6, t.getId());
    }

    @Override
    protected String getTextQueryInsert() {
        return "INSERT INTO STUDENTS (FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DAY, GROUP_ID) values(?, ?, ?, ?, ?);";
    }

    @Override
    protected void setParametersInsert(PreparedStatement ps, T t) throws SQLException {
        ps.setString(1, t.getFirstName());
        ps.setString(2, t.getMiddleName());
        ps.setString(3, t.getLastName());
        ps.setDate(4, new Date(t.getBirthDay().getTime()));
        ps.setLong(5, t.getGroupId());

    }
}
