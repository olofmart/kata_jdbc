package ru.olmart;

import ru.olmart.dao.UserDao;
import ru.olmart.dao.UserDaoJDBCImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Name1", "LastName1", (byte) 20);
        System.out.println(userDao.getAllUsers().get(0));
        userDao.saveUser("Name2", "LastName2", (byte) 25);
        System.out.println(userDao.getAllUsers().get(1));
        userDao.saveUser("Name3", "LastName3", (byte) 31);
        System.out.println(userDao.getAllUsers().get(2));
        userDao.saveUser("Name4", "LastName4", (byte) 38);
        System.out.println(userDao.getAllUsers().get(3));

        System.out.println(userDao.getAllUsers());
        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}