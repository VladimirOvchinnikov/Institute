package com.haulmont.testtask.db.init;

import com.haulmont.testtask.db.ConnectDB;
import com.haulmont.testtask.exception.CriticalException;

import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Leon on 08.06.2016.
 */
public class InitializtionDB {

    public static void createFile() {
        File file = new File("src/main/resources/create_table.script");
        System.out.println(file.getAbsolutePath());
        //FileInputStream fis = new FileInputStream(file);
        //InputStreamReader isr = new InputStreamReader(fis);


        StringBuilder script = new StringBuilder();
        //try (Scanner scanner = new Scanner(file)) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
//            while (scanner.hasNext()) {
//                script.append(scanner.next());
//                script.append(" ");
//            }
            String s = null;
            while ((s = br.readLine()) != null) {
                script.append(s);
            }
            //System.out.println(script.toString());
        } catch (IOException ioe) {
            System.err.println("Error " + ioe.getMessage());
            ioe.printStackTrace();
        }

        try {
            ConnectDB connectDB = new ConnectDB();
            connectDB.connect();
            try (Statement statement = connectDB.getConnection().createStatement()) {
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
