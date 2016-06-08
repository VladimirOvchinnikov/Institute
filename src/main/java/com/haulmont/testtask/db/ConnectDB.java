package com.haulmont.testtask.db;

import com.haulmont.testtask.exception.CriticalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
            throw new CriticalException("Error: not load class driver (org.hsqldb.jdbcDriver)", e);
        }
    }

    public void connect() throws CriticalException {
        try {
            connection = DriverManager.getConnection(
                        "jdbc:hsqldb:file:dbpath/dbname", "SA", "");
        } catch (SQLException e) {
            throw new CriticalException("Error: not connect on db ", e);
        }

    }

    public  void close(){
        try (Statement statement = connection.createStatement()){
            statement.execute("SHUTDOWN");
        } catch (SQLException e) {
            System.err.println("Error: close db");
            e.printStackTrace();
        }
    }
}
