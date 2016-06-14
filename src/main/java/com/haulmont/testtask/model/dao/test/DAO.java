package com.haulmont.testtask.model.dao.test;

import com.haulmont.testtask.model.entity.Entity;

import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface DAO<E extends Entity, T> {

    public List<T> select(List<E> ids);
    //public List<T> selectAll();
    public int delete(List<E> obj);
    public boolean update(T t);
    public Long insert(T obj);
}
