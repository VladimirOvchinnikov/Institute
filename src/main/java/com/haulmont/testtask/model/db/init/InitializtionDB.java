package com.haulmont.testtask.model.db.init;

import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.exception.CriticalException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Leon on 08.06.2016.
 */
public class InitializtionDB {

    final private static String PATH = "src/main/resources/";
    //private static String name1 = "create_table.script";
    //private static String name2 = "insert_test_data.script";

    public static void init(String nameFile){
        File file = new File(PATH + nameFile);
        StringBuilder script = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = null;
            while ((s = br.readLine()) != null) {
                script.append(s);
            }
        } catch (IOException ioe) {
            System.err.println("Error " + ioe.getMessage());
            ioe.printStackTrace();
        }
        try {
            ConnectDB.getInstance();
            try (Statement statement = ConnectDB.getInstance().getConnection().createStatement()) {
                statement.execute(script.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (CriticalException e) {
            e.printStackTrace();
        }
    }
}
