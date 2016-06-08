package com.haulmont.testtask.db;

import com.haulmont.testtask.db.init.InitializtionDB;

/**
 * Created by Leon on 08.06.2016.
 */
public class Main {

    public static void main(String[] args){
        /*try {
            ConnectDB.connect();
        } catch (CriticalException e) {
            System.err.println(e.getMessage());
        }*/
        InitializtionDB.createFile();
    }
}
