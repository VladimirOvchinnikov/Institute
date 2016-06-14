package com.haulmont.testtask.controller.adapter;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface ViewToEntity<ViewEntity, Entity> {
    public Entity apply(ViewEntity entity);
}
