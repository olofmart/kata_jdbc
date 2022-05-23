package ru.olmart.dao;

import ru.olmart.model.User;
import ru.olmart.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getMySQLConnection();

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {

        try(Statement statement = connection.createStatement()) {
            String createTable = "CREATE  TABLE IF NOT EXISTS users(id INT AUTO_INCREMENT, " +
                    " name VARCHAR(50), last_name VARCHAR (50), age INT not NULL, PRIMARY KEY (id));";
            statement.execute(createTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            String dropTable = "DROP TABLE IF EXISTS users;";
            statement.execute(dropTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String insertUser = "INSERT INTO users(name, last_name, age) VALUES (?, ?, ?);";
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
        String removeUser = "DELETE FROM users WHERE id = ?;";
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

        String allUsers = "SELECT * FROM users;";

        try(Statement statement = connection.createStatement()) {
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
            String dropTable = "DELETE FROM users;";
            statement.executeUpdate(dropTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
