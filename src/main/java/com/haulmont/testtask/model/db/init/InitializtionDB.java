package com.haulmont.testtask.model.db.init;

import com.haulmont.testtask.model.db.ConnectDB;
import com.haulmont.testtask.model.db.exception.DatabaseException;

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

    public static void init() throws DatabaseException {
        File file = new File(PATH + "create_table.script");
        StringBuilder script = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String s = null;
            while ((s = br.readLine()) != null) {
                script.append(s);
            }
            ConnectDB.getInstance();
            try (Statement statement = ConnectDB.getInstance().getConnection().createStatement()) {
                statement.execute(script.toString());
            }
        } catch (DatabaseException | SQLException e) {
            throw new DatabaseException(e);
        } catch (IOException ioe) {
            throw new DatabaseException("ERROR not founded init file create_table.script");
        }
    }
}
