package com.haulmont.testtask.db;

import com.haulmont.testtask.exception.CriticalException;

import java.sql.Connection;

/**
 * Created by Leon on 08.06.2016.
 */
public class ConnectDB {

    public static Connection connection;

    public Connection getConnection(){
        return connection;
    }
    public ConnectDB() throws CriticalException {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new CriticalException(e);
        }
    }

//    public static void connect() throws CriticalException {
//
//        try {
//            connection = DriverManager.getConnection(
//                    "jdbc:hsqldb:file:dbpath/dbname", "SA", "");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        close();
//    }
//
//    public static void close(){
//        try (Statement statement = connection.createStatement()){
//            statement.execute("SHUTDOWN");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
