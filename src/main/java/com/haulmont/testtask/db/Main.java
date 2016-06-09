package com.haulmont.testtask.db;

import com.haulmont.testtask.db.init.InitializtionDB;

/**
 * Created by Leon on 08.06.2016.
 */
public class Main {

    public static void main(String[] args){
        try {
            /*ConnectDB.getInstance();
            ConnectDB.getInstance().getConnection();
            ConnectDB.getInstance().getConnection();
            ConnectDB.getInstance().close();*/
            InitializtionDB.createFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
