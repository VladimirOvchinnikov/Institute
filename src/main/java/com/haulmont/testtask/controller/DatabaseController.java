package com.haulmont.testtask.controller;

import com.haulmont.testtask.model.db.exception.DatabaseException;
import com.haulmont.testtask.model.db.ConnectDB;

/**
 * Created by Leon on 15.06.2016.
 */
public class DatabaseController {

    public static void openConnect(){
        try {
            ConnectDB.getInstance();
        } catch (DatabaseException e) {

        }
    }

    public static void closeConnect(){
        try {
            ConnectDB.getInstance().close();
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }
}
