package com.haulmont.testtask.controller.adapter;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface Converter<Entity, ViewEntity> {
    public ViewEntity apply(Entity entity);
}
