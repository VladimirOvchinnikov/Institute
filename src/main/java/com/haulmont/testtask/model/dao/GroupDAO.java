package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public class GroupDAO<E extends Entity, T extends Group> extends AbstractDAO<E, T> implements DAO<E, T> {

    @Override
    protected String getTextQuerySelect(int count) {
        StringBuilder sql = new StringBuilder("SELECT * FROM GROUPS");
        if (count > 0) {
            sql.append(" WHERE ID IN (");
            for (int i = 0; i < count - 1; i++) {
                sql.append("?, ");
            }
            sql.append("?)");
        }
        sql.append(";");
        return sql.toString();
    }

    @Override
    protected T getEntity(ResultSet rs) throws DAOException {
        Group group = new Group();
        try {
            group.setId(rs.getLong("id"));
            group.setNumber(rs.getInt("number"));
            group.setFaculty(rs.getString("faculty"));
            return (T) group;
        } catch (SQLException e) {
            throw new DAOException(" method getEntity ");
        }
    }

    @Override
    protected String getTextQueryDelete(int count) {
        StringBuilder sql = new StringBuilder("DELETE FROM GROUPS WHERE ID in (");
        for (int i = 0; i < count - 1; i++) {
            sql.append("?, ");
        }
        sql.append("?);");
        return sql.toString();
    }

    @Override
    protected String getTextQueryUpdate() {
        return "UPDATE GROUPS SET NUMBER = ? , FACULTY = ? WHERE ID = ?";
    }

    @Override
    protected void setParametersUpdate(PreparedStatement ps, T t) throws DAOException {
        try {
            ps.setInt(1, t.getNumber());
            ps.setString(2, t.getFaculty());
            ps.setLong(3, t.getId());
        } catch (SQLException e) {
            throw new DAOException(" method setParametersUpdate");
        }
    }

    @Override
    protected String getTextQueryInsert() {
        return "INSERT INTO GROUPS (number, faculty) values(?,?)";
    }

    @Override
    protected void setParametersInsert(PreparedStatement ps, T t) throws DAOException {
        try {
            ps.setInt(1, t.getNumber());
            ps.setString(2, t.getFaculty());
        } catch (SQLException e) {
            throw new DAOException(" method setParametersInsert");
        }
    }
}
