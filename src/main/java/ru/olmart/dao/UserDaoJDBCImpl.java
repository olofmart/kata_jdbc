package ru.olmart.dao;

import ru.olmart.model.User;
import ru.olmart.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String tableName = "users";
    private final Connection connection = Util.getMySQLConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {

        try(Statement statement = connection.createStatement()) {
            if (isTableExist(tableName)) {
                System.out.println("Таблица уже существует");
                return;
            }
            String createTable = "CREATE TABLE " + tableName +
                    "(id INT AUTO_INCREMENT, " +
                    " name VARCHAR(50), " +
                    " last_name VARCHAR (50), " +
                    " age INT not NULL, " +
                    " PRIMARY KEY (id));";
            statement.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            if (!isTableExist(tableName)) {
                System.out.println("В базе данных нет таблиц, поэтому удалять нечего");
                return;
            }
            String dropTable = "DROP TABLE " + tableName + ";";
            statement.execute(dropTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        if (!isTableExist(tableName)) {
            System.out.println("Надо создать таблицу");
            return;
        }
        String insertUser = "INSERT INTO " + tableName + "(name, last_name, age) " + "VALUES (?, ?, ?);";
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        if (!isTableExist(tableName)) {
            System.out.println("Надо создать таблицу");
            return;
        }
        String removeUser = "DELETE FROM " + tableName + " WHERE id = ?;";
        try(PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String allUsers = "SELECT * FROM " + tableName + ";";

        try(Statement statement = connection.createStatement()) {
            if (!isTableExist(tableName)) {
                System.out.println("Запрашиваемой таблицы нет");
                return null;
            }

            ResultSet resultSet = statement.executeQuery(allUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try(Statement statement = connection.createStatement()) {
            if (isTableExist(tableName)) {
                String dropTable = "DELETE FROM " + tableName;
                statement.executeUpdate(dropTable);
            } else {
                System.out.println("В базе данных нет таблиц, поэтому удалять нечего");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isTableExist(String tableName) {
        boolean tabExists = false;
        try (ResultSet rs = connection.createStatement().executeQuery("Show tables")) {
            while (rs.next()) {
                String tName = rs.getString(1);
                if (tName != null && tName.equals(tableName)) {
                    tabExists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tabExists;
    }
}
