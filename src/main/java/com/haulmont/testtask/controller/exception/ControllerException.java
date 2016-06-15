package com.haulmont.testtask.controller.exception;

/**
 * Created by Leon on 15.06.2016.
 */
public class ControllerException extends Exception {
    public ControllerException(Exception e) {
        super(e);
    }

    public ControllerException(String msg, Exception e) {
        super(msg, e);
    }
}
