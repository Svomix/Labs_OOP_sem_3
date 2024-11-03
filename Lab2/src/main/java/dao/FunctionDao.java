package dao;

import entities.Function;
import org.hibernate.query.Query;
import util.HibernateUtil;

public class FunctionDao {
    public Function create(Function ent) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(ent);
            session.getTransaction().commit();
        }
        return ent;
    }

    public Function read(String query) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Function> query1 = session.createQuery(query, Function.class);
            session.getTransaction().commit();
            return query1.uniqueResult();
        }
    }

    public Function update(Function ent) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(ent);
            session.getTransaction().commit();
        }
        return ent;
    }

    public void delete(Function ent) {
        try(var sessionFactory = HibernateUtil.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(ent);
            session.getTransaction().commit();
        }
    }
}
