package com.haulmont.testtask.model.dao;

import com.haulmont.testtask.model.entity.Entity;

import java.util.List;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface DAO<E extends Entity, T> {

    public List<T> select(E e);
    public List<T> selectAll();
    public boolean delete(E e);
    public T update(T t);
    public List<T> insert(List<T> list);
}
