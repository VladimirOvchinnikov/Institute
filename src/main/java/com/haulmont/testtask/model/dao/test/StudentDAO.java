package com.haulmont.testtask.model.dao.test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Student;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentDAO<E extends Entity, T extends Student> implements DAO<E, T> {
    @Override
    public List<T> select(List<E> ids) {
        List<T> result = Lists.newArrayList();
        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENTS");
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
                    T student = (T) new Student();
                    student.setId(rs.getLong("id"));
                    student.setFirstName(rs.getString("first_name"));
                    student.setMiddleName(rs.getString("middle_name"));
                    student.setLastName(rs.getString("last_name"));
                    student.setBirthDay(rs.getDate("birth_day"));
                    student.setGroupId(rs.getLong("group_id"));
                    result.add(student);
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

        StringBuilder sql = new StringBuilder("DELETE FROM STUDENTS WHERE ID in (");
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
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("UPDATE STUDENTS " +
                "SET FIRST_NAME = ? , MIDDLE_NAME = ?, LAST_NAME = ?, BIRTH_DAY = ?, GROUP_ID = ? WHERE ID = ?")) {
            preparedStatement.setString(1, t.getFirstName());
            preparedStatement.setString(2, t.getMiddleName());
            preparedStatement.setString(3, t.getLastName());
            preparedStatement.setDate(4, new Date(t.getBirthDay().getTime()));
            preparedStatement.setLong(5, t.getGroupId());
            preparedStatement.setLong(6, t.getId());

            if (preparedStatement.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
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
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("INSERT INTO " +
                "STUDENTS (FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DAY, GROUP_ID) values(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, obj.getFirstName());
            preparedStatement.setString(2, obj.getMiddleName());
            preparedStatement.setString(3, obj.getLastName());
            preparedStatement.setDate(4, new Date(obj.getBirthDay().getTime()));
            preparedStatement.setLong(5, obj.getGroupId());

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

    public Map<Long, Integer> selectNumberGroupStudents(List<T> list){//это для selectAll
        StringBuilder sql = new StringBuilder("SELECT STUDENTS.ID AS ID, GROUPS.NUMBER AS NUMBER FROM STUDENTS JOIN GROUPS ON STUDENTS.GROUP_ID = GROUPS.ID");
        Map<Long, Integer> map = Maps.newHashMap();
        if (list.size() > 0) {
            sql.append("WHERE STUDENTS.ID IN (");
            for (int i = 0; i < list.size() - 1; i++) {
                sql.append("?, ");
            }
            sql.append("?);");
        }
        try(PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sql.toString())){
            int i = 1;
            for(T t: list){
                preparedStatement.setLong(i, t.getId());
                i++;
            }

            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
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

    public Map<Long, Integer> selectDistinctNumberGroup(){//это для выбора
        Map<Long, Integer> map = Maps.newHashMap();
        try(PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("SELECT ID, NUMBER FROM GROUPS")){
            try(ResultSet rs = preparedStatement.executeQuery()){
                while (rs.next()){
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
}
