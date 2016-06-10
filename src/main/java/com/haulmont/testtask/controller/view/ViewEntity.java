package com.haulmont.testtask.controller.view;

/**
 * Created by ovchinnikov on 10.06.2016.
 */
public interface ViewEntity {

    public long getId();        //не забываем про unchecked
    public void setId(long id); //не забываем про unchecked
}
