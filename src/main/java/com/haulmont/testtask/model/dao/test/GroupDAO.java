package com.haulmont.testtask.model.dao.test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.exception.CriticalException;
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
public class GroupDAO<E extends Entity, T extends Group> implements DAO<E, T> {

    @Override
    public String sqlSelectBuilder(List<E> entities) {
        StringBuilder sql = new StringBuilder("SELECT * FROM GROUPS");
        if (entities.size() > 0) {
            sql.append(" WHERE ID IN (");
            for (int i = 0; i < entities.size() - 1; i++) {
                sql.append("?, ");
            }
            sql.append("?)");
        }
        sql.append(";");
        return sql.toString();
    }

    @Override
    public T getEntity(ResultSet rs) throws SQLException {
        T group = (T) new Group();
        group.setId(rs.getLong("id"));
        group.setNumber(rs.getInt("number"));
        group.setFaculty(rs.getString("faculty"));
        return group;
    }

    @Override
    public String sqlDeleteBuilder(List<E> entities) {
        StringBuilder sql = new StringBuilder("DELETE FROM GROUPS WHERE ID in (");
        for (int i = 0; i < entities.size() - 1; i++) {
            sql.append("?, ");
        }
        sql.append("?);");
        return sql.toString();
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE GROUPS SET NUMBER = ? , FACULTY = ? WHERE ID = ?";
    }

    @Override
    public void setParametersUpdate(PreparedStatement ps, T t) throws SQLException {
        setParametersInsert(ps, t);
        ps.setLong(3, t.getId());
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO GROUPS (number, faculty) values(?,?)";
    }

    @Override
    public void setParametersInsert(PreparedStatement ps, T t) throws SQLException {
        ps.setInt(1, t.getNumber());
        ps.setString(2, t.getFaculty());
    }
}
