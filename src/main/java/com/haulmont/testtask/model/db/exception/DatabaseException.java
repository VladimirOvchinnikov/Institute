package com.haulmont.testtask.model.db.exception;

/**
 * Created by Leon on 08.06.2016.
 */
public class DatabaseException extends Exception {

    public DatabaseException(Exception e) {
        super(e);
    }

    public DatabaseException(String msg, Exception e) {
        super(msg, e);
    }
}
