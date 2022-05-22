package ru.olmart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;

    private Util () {

    }

    public static Connection getMySQLConnection() {
        if (connection != null) {
            return connection;
        }
        String hostName = "localhost";
        String dbName = "kata";
        String userName = "root";
        String password = "1234qwer";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection (String hostName, String dbName, String userName, String password) {
        try {
            String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
            connection = DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection created");
        return connection;
    }
}
