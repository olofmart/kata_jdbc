package ru.olmart.dao;

import org.hibernate.Session;
import ru.olmart.model.User;
import ru.olmart.util.Util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSession();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

    }

    @Override
    public void dropUsersTable() {

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        session.beginTransaction();
        session.delete(session.get(User.class, id));
        session.getTransaction().commit();
    }

    @Override
    public List<User> getAllUsers() {
        session.beginTransaction();
        List<User> usersList = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
//        CriteriaBuilder builder = session.getCriteriaBuilder();
//        CriteriaQuery<User> criteria = builder.createQuery(User.class);
//        criteria.from(User.class);
//        List<User> usersList = session.createQuery(criteria).getResultList();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        session.beginTransaction();
        session.createQuery("delete User");
        session.getTransaction().commit();
    }
}

