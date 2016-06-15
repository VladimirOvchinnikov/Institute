package com.haulmont.testtask.controller.exception;

/**
 * Created by Leon on 15.06.2016.
 */
public class ControllerCriticalException extends Exception {

    public ControllerCriticalException(Exception e) {
        super(e);
    }

    public ControllerCriticalException(String msg, Exception e) {
        super(msg, e);
    }
}
