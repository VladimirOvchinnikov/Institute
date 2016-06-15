package com.haulmont.testtask.model.dao.exception;

/**
 * Created by Leon on 15.06.2016.
 */
public class DAOCriticalException extends Exception {
    public DAOCriticalException(Exception e) {
        super(e);
    }

    public DAOCriticalException(String msg, Exception e) {
        super(msg, e);
    }
    public DAOCriticalException(String msg) {
        super(msg);
    }
}
