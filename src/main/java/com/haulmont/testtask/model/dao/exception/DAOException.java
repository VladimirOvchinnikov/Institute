package com.haulmont.testtask.model.dao.exception;

/**
 * Created by Leon on 15.06.2016.
 */
public class DAOException extends Exception {
    public DAOException(Exception e) {
        super(e);
    }

    public DAOException(String msg) {
        super(msg);
    }
}
