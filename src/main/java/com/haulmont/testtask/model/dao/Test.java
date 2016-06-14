package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.exception.CriticalException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.entity.Entity;
import com.haulmont.testtask.model.entity.Group;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ovchinnikov on 14.06.2016.
 */
public class Test {

    public String builderSql(){
        String nameTable = "GROUPS";
        String updateParam = "NUMBER = ? , FACULTY = ?";
        String sql = "UPDATE " + nameTable + " SET " + updateParam + " WHERE ID = ?";
        return sql;
    }

    public void setParam(PreparedStatement ps, Group t) throws SQLException {
        ps.setInt(1, t.getNumber());
        ps.setString(2, t.getFaculty());
        ps.setLong(3, t.getId());
    }

    public boolean update(Group t) {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(builderSql())) {
            setParam(preparedStatement, t);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (CriticalException e) {
            e.printStackTrace();
            return false;
        }
    }
}
