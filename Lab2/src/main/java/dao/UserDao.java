package dao;

import entities.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class UserDao implements DAO<User>
{
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public User create(User entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public User read(String query) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<User> query1 = session.createNativeQuery(query, User.class);
            session.getTransaction().commit();
            return query1.uniqueResult();
        }
    }

    @Override
    public User update(User entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public void delete(User entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}
