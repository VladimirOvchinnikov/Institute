package com.haulmont.testtask.controller;

import com.haulmont.testtask.controller.exception.ControllerException;
import com.haulmont.testtask.model.db.exception.DatabaseException;
import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.db.init.InitializtionDB;

/**
 * Created by Leon on 15.06.2016.
 */
public class DatabaseController {

    public static void openConnect() throws ControllerException {
        try {
            ConnectDB.getInstance();
            InitializtionDB.init();
        } catch (DatabaseException e) {
            throw new ControllerException(e);
        }
    }

    public static void closeConnect() throws ControllerException {
        try {
            ConnectDB.getInstance().close();
        } catch (DatabaseException e) {
            throw new ControllerException(e);
        }
    }
}
