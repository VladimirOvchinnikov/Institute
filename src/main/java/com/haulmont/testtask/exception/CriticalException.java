package com.haulmont.testtask.exception;

/**
 * Created by Leon on 08.06.2016.
 */
public class CriticalException extends Exception {

    public CriticalException(Exception e) {
        super(e);
    }

    public CriticalException(String msg, Exception e) {
        super(msg, e);
    }
}
