package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.model.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Leon on 14.06.2016.
 */
public interface SQLHelper<E extends Entity, T> {

    public String sqlSelectBuilder(List<E> entities);
    //public T getEntity(ResultSet rs) throws SQLException;

    public String sqlDeleteBuilder(List<E> entities);

    public String getUpdateSql();
    public void setParametersUpdate(PreparedStatement ps, T t) throws SQLException;

    public String getInsertSql();
    //public void setParametersInsert(PreparedStatement ps, T t) throws SQLException;
}
