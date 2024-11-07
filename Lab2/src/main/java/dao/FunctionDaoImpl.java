package dao;

import entities.FunctionEntity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class FunctionDaoImpl implements DAO<FunctionEntity> {
    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();

    public FunctionEntity create(FunctionEntity ent) {
        try(var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(ent);
            session.getTransaction().commit();
        }
        return ent;
    }
    public FunctionEntity read(String query) {
        try(var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<FunctionEntity> query1 = session.createNativeQuery(query, FunctionEntity.class);
            session.getTransaction().commit();
            return query1.uniqueResult();
        }
    }
    public FunctionEntity update(FunctionEntity ent) {
        try(var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(ent);
            session.getTransaction().commit();
        }
        return ent;
    }
    public void delete(FunctionEntity ent) {
        try(var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(ent);
            session.getTransaction().commit();
        }
    }
}
