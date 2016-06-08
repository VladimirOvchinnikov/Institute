package com.haulmont.testtask.db.init;

import com.haulmont.testtask.db.ConnectDB;
import com.haulmont.testtask.exception.CriticalException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Leon on 08.06.2016.
 */
public class InitializtionDB {

    public static void createFile() {
        File file = new File("create_table.script");
        StringBuilder script = new StringBuilder();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                script.append(scanner.next());
                script.append(" ");
            }
        } catch (IOException ioe) {
            System.err.println("Error");
        }

        try {
            ConnectDB connectDB = new ConnectDB();
            connectDB.connect();
            try (Statement statement = connectDB.getConnection().createStatement()){
                statement.execute(script.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectDB.close();
        } catch (CriticalException e) {
            e.printStackTrace();
        }


    }
}
