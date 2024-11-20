package Labs_OOP_sem_3.dao;

import Labs_OOP_sem_3.entities.UserEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import Labs_OOP_sem_3.util.HibernateUtil;

public class UserDao implements DAO<UserEntity> {
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public UserEntity create(UserEntity entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public UserEntity read(String query) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<UserEntity> query1 = session.createNativeQuery(query, UserEntity.class);
            session.getTransaction().commit();
            return query1.uniqueResult();
        }
    }

    @Override
    public UserEntity update(UserEntity entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public void delete(UserEntity entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}
