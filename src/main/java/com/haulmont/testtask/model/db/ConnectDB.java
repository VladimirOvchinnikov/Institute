package com.haulmont.testtask.model.db;

import com.haulmont.testtask.exception.CriticalException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Leon on 08.06.2016.
 */
public class ConnectDB {

    private Connection connection;


    private String dbPath = "dbpath";// можно все в проперти
    private String dbName = "dbname";// можно все в проперти
    private String dbUser = "SA";    // можно все в проперти
    private String dbPassword = "";  // можно все в проперти
                                     // или хотя бы оформить как константы

    private static ConnectDB instance;

    public Connection getConnection(){
        return connection;
    }

    public static ConnectDB getInstance() throws CriticalException {
        ConnectDB localInstance = instance;
        if (localInstance == null){
            synchronized (ConnectDB.class){
                localInstance = instance;
                if (localInstance == null){
                    try {
                        instance = localInstance = new ConnectDB();
                        instance.connect();
                    }catch (CriticalException ce){
                        ce.printStackTrace();
                        throw new CriticalException("Error: init database \n" + ce.getMessage(), ce);
                    }
                }
            }
        }
        return localInstance;
    }

    private ConnectDB() throws CriticalException {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new CriticalException("Error: not load class driver (org.hsqldb.jdbcDriver)", e);
        }
    }

    public void connect() throws CriticalException {
        try {
            connection = DriverManager.getConnection(
                        "jdbc:hsqldb:file:" + dbPath+ "/" + dbName, dbUser, dbPassword);
        } catch (SQLException e) {
            ConnectDB.instance = null;
            e.printStackTrace();
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
