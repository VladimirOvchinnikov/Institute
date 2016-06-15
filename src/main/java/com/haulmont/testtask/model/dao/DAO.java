package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.model.dao.exception.DAOCriticalException;
import com.haulmont.testtask.model.dao.exception.DAOException;
import com.haulmont.testtask.model.entity.Entity;

import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface DAO<E extends Entity, T> {

    public List<T> select(List<E> entities) throws DAOException, DAOCriticalException;
    public int delete(List<E> entities) throws DAOCriticalException, DAOException;
    public boolean update(T obj) throws DAOException, DAOCriticalException;
    public Long insert(T obj) throws DAOException, DAOCriticalException;

}
