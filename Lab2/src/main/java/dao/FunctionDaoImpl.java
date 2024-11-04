package dao;

import entities.FunctionEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FunctionDaoImpl implements FunctionDAO{
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public FunctionEntity create(FunctionEntity ent) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(ent);
        return ent;
    }
    @Override
    public FunctionEntity read(String query) {
        Session session = sessionFactory.getCurrentSession();
        Query<FunctionEntity> q = session.createQuery(query, FunctionEntity.class);
        return q.uniqueResult();
    }
    @Override
    public FunctionEntity update(FunctionEntity ent) {
        Session session = sessionFactory.getCurrentSession();
        session.update(ent);
        return ent;
    }

    @Override
    public void delete(FunctionEntity ent) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(ent);
    }
}
