package com.haulmont.testtask.view.component.modal.window;

import com.haulmont.testtask.controller.view.ViewEntity;

/**
 * Created by ovchinnikov on 15.06.2016.
 */
public class StudentModalWindow extends BasicModalWindow {
    public StudentModalWindow(String name) {
        super(name);
    }

    public StudentModalWindow(String name, ViewEntity view) {
        super(name, view);
    }

    @Override
    public void add() {

    }

    @Override
    public boolean update(ViewEntity view) {
        return false;
    }
}
