package com.haulmont.testtask.model.db;

import com.haulmont.testtask.model.db.init.InitializtionDB;

/**
 * Created by Leon on 08.06.2016.
 */
public class MainDB {

    public static void main(String[] args){
        try {
            /*ConnectDB.getInstance();
            ConnectDB.getInstance().getConnection();
            ConnectDB.getInstance().getConnection();
            ConnectDB.getInstance().close();*/
            InitializtionDB.init("create_table.script");
            InitializtionDB.init("insert_test_data.script");

            ConnectDB.getInstance().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
