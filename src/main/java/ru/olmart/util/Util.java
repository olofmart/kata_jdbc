package ru.olmart.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;

    private Util () {

    }

    public static Session getSession() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/kata");
        properties.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");

        properties.setProperty("hibernate.connection.username", "root");
        properties.setProperty("hibernate.connection.password", "1234qwer");
        properties.setProperty("hibernate.connection.driver_class", "com.musql.jdbc.Driver");
        properties.setProperty("show_sql", true); //If you wish to see the generated sql query

        SessionFactory sessionFactory = new Configuration().addProperties(properties).buildSessionFactory();
        Session session = sessionFactory.openSession();
        //session.beginTransaction();  //todo вынести в дао

        return session;
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
