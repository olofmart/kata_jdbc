package ru.olmart.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "kata";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "1234qwer";
    private static Connection connection = null;

    private Util () {

    }

    public static Connection getMySQLConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;
            connection = DriverManager.getConnection(connectionURL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection created");
        return connection;
    }
}
