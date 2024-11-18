package Labs_OOP_sem_3.dao;

import Labs_OOP_sem_3.entities.PointEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import Labs_OOP_sem_3.util.HibernateUtil;

public class PointDao implements DAO<PointEntity> {
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    @Override
    public PointEntity create(PointEntity entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public PointEntity read(String query) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<PointEntity> query1 = session.createNativeQuery(query, PointEntity.class);
            session.getTransaction().commit();
            return query1.uniqueResult();
        }
    }

    @Override
    public PointEntity update(PointEntity entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
        return entity;
    }

    @Override
    public void delete(PointEntity entity) {
        try (var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }
}
