package com.haulmont.testtask.controller.adapter;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface EntityToView<Entity, ViewEntity> {
    public ViewEntity apply(Entity entity);
    //на до сделать что-то вроде лямды apply для перевода
}
