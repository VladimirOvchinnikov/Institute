package com.haulmont.testtask.model.dao;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.db.exception.DatabaseException;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Student;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Leon on 12.06.2016.
 */
public class StudentDAO<E extends Entity, T extends Student> extends AbstractDAO<E, T> implements DAO<E, T> {

    public Map<Long, Integer> selectNumberGroupStudents(List<T> list) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT STUDENTS.ID AS ID, GROUPS.NUMBER AS NUMBER FROM STUDENTS JOIN GROUPS ON STUDENTS.GROUP_ID = GROUPS.ID ");
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
            throw new DAOException("SQL ERROR: selectNumberGroupStudents " + this.getClass());
        } catch (DatabaseException e) {
            throw new DAOException(e);
        }
    }

    public Map<Long, Integer> selectDistinctNumberGroup() throws DAOException {
        Map<Long, Integer> map = Maps.newHashMap();
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement("SELECT ID, NUMBER FROM GROUPS")) {
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getLong("id"), rs.getInt("number"));
                }
            }
            return map;
        } catch (SQLException e) {
            throw new DAOException("SQL ERROR: selectDistinctNumberGroup " + this.getClass());
        } catch (DatabaseException e) {
            throw new DAOException(e);
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
    protected T getEntity(ResultSet rs) throws DAOException {
        T student = (T) new Student();
        try {
            student.setId(rs.getLong("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setMiddleName(rs.getString("middle_name"));
            student.setLastName(rs.getString("last_name"));
            student.setBirthDay(rs.getDate("birth_day"));
            student.setGroupId(rs.getLong("group_id"));
            return student;
        } catch (SQLException e) {
            throw new DAOException(" method getEntity ");
        }
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
        return "UPDATE STUDENTS SET FIRST_NAME = ? , MIDDLE_NAME = ?, LAST_NAME = ?, BIRTH_DAY = ?, GROUP_ID = ? WHERE ID = ?";
    }

    @Override
    protected void setParametersUpdate(PreparedStatement ps, T obj) throws DAOException {
        try {
            ps.setString(1, obj.getFirstName());
            ps.setString(2, obj.getMiddleName());
            ps.setString(3, obj.getLastName());
            ps.setDate(4, new Date(obj.getBirthDay().getTime()));
            ps.setLong(5, obj.getGroupId());
            ps.setLong(6, obj.getId());
        } catch (SQLException e) {
            throw new DAOException(" method setParametersUpdate");
        }
    }

    @Override
    protected String getTextQueryInsert() {
        return "INSERT INTO STUDENTS (FIRST_NAME, MIDDLE_NAME, LAST_NAME, BIRTH_DAY, GROUP_ID) values(?, ?, ?, ?, ?);";
    }

    @Override
    protected void setParametersInsert(PreparedStatement ps, T obj) throws DAOException {
        try {
            ps.setString(1, obj.getFirstName());
            ps.setString(2, obj.getMiddleName());
            ps.setString(3, obj.getLastName());
            ps.setDate(4, new Date(obj.getBirthDay().getTime()));
            ps.setLong(5, obj.getGroupId());
        } catch (SQLException e) {
//            throw new DAOException("wrong set data for insert " + this.getClass() + e.getMessage(), e);
            throw new DAOException(" method setParametersInsert");
        }
    }

    public List<Student> filter(String filterLastName, Integer filterNumberGroup) throws DAOException {
        StringBuilder sql = new StringBuilder("SELECT * FROM STUDENTS JOIN GROUPS ON STUDENTS.GROUP_ID = GROUPS.ID WHERE ");//"STUDENTS.LAST_NAME = ? AND GROUPS.NUMBER = ?";
        List<Student> students = Lists.newArrayList();
        if (!filterLastName.isEmpty() && filterNumberGroup != null) {
            sql.append("STUDENTS.LAST_NAME = ? AND GROUPS.NUMBER = ?;");
        } else if (!filterLastName.isEmpty()) {
            sql.append("STUDENTS.LAST_NAME = ?;");
        } else if (filterNumberGroup != null) {
            sql.append("GROUPS.NUMBER = ?;");
        } else {
            return students;
        }

        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(sql.toString())) {
            if (!filterLastName.isEmpty() && filterNumberGroup != null) {
                preparedStatement.setString(1, filterLastName);
                preparedStatement.setInt(2, filterNumberGroup);
            } else if (!filterLastName.isEmpty()) {
                preparedStatement.setString(1, filterLastName);
            } else if (filterNumberGroup != null) {
                preparedStatement.setInt(1, filterNumberGroup);
            }


            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    students.add(getEntity(rs));
                }
            }
            return students;
        } catch (SQLException e) {
            throw new DAOException("SQL ERROR: filter " + this.getClass());
        } catch (DatabaseException e) {
            throw new DAOException(e);
        }
    }
}
