package com.haulmont.testtask.model.db;

import com.haulmont.testtask.model.db.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Leon on 08.06.2016.
 */
public class ConnectDB {

    private Connection connection;

    private String dbPath = "dbpath";
    private String dbName = "dbname";
    private String dbUser = "SA";
    private String dbPassword = "";

    private static ConnectDB instance;

    public Connection getConnection() {
        return connection;
    }

    public static ConnectDB getInstance() throws DatabaseException {
        ConnectDB localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectDB.class) {
                localInstance = instance;
                if (localInstance == null) {
                    try {
                        instance = localInstance = new ConnectDB();
                        instance.connect();
                    } catch (DatabaseException ce) {
                        ce.printStackTrace();
                        throw new DatabaseException("Error: init database \n" + ce.getMessage(), ce);
                    }
                }
            }
        }
        return localInstance;
    }

    private ConnectDB() throws DatabaseException {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("DB ERROR", e);
        }
    }

    private void connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:hsqldb:file:" + dbPath + "/" + dbName, dbUser, dbPassword);
        } catch (SQLException e) {
            ConnectDB.instance = null;
            throw new DatabaseException("Error: not connect database ", e);
        }

    }

    public void close() throws DatabaseException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("SHUTDOWN");
        } catch (SQLException e) {
            throw new DatabaseException("SQL ERROR", e);
        }
    }
}
