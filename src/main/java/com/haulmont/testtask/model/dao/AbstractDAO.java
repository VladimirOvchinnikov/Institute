package com.haulmont.testtask.model.dao;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.db.exception.DatabaseException;
import com.haulmont.testtask.model.entity.Entity;

import java.sql.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ovchinnikov on 14.06.2016.
 */
public abstract class AbstractDAO<E extends Entity, T> implements DAO<E, T> {

    @Override
    public List<T> select(List<E> entities) throws DAOException {

        List<T> result = Lists.newArrayList();
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getTextQuerySelect(entities.size()))) {
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
            throw new DAOException("SQL ERROR: select " + getClass());
        } catch (DatabaseException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int delete(List<E> entities) throws DAOException {
        if (entities.size() == 0) {
            return 0;
        }
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getTextQueryDelete(entities.size()))) {
            List<Long> ids = entities.stream().map(Entity::getId).collect(Collectors.toList());
            for (int i = 1; i < entities.size() + 1; i++) {
                preparedStatement.setLong(i, entities.get(i - 1).getId());
            }
            return preparedStatement.executeUpdate();
        } catch (DatabaseException e) {
            throw new DAOException(e);
        } catch (SQLIntegrityConstraintViolationException e) {
            return 0;
        } catch (SQLException e1) {
            throw new DAOException("SQL ERROR: delete " + getClass());
        }
    }

    @Override
    public boolean update(T obj) throws DAOException {

        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getTextQueryUpdate())) {
            setParametersUpdate(preparedStatement, obj);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException("SQL ERROR: update " + getClass());
        } catch (DatabaseException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Long insert(T obj) throws DAOException {
        try (PreparedStatement preparedStatement = ConnectDB.getInstance().getConnection().prepareStatement(getTextQueryInsert(), Statement.RETURN_GENERATED_KEYS)) {
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
            throw new DAOException("SQL ERROR: insert " + getClass());
        } catch (DatabaseException e) {
            throw new DAOException(e);
        }
    }

    protected abstract void setParametersInsert(PreparedStatement ps, T obj) throws DAOException;

    protected abstract void setParametersUpdate(PreparedStatement ps, T obj) throws DAOException;

    protected abstract T getEntity(ResultSet rs) throws DAOException;

    protected abstract String getTextQueryInsert();

    protected abstract String getTextQueryUpdate();


    protected abstract String getTextQueryDelete(int count);

    protected abstract String getTextQuerySelect(int count);
}
