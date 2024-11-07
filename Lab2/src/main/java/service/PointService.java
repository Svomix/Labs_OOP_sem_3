package service;

import dao.DAO;
import dao.PointDao;
import entities.PointEntity;

public class PointService implements Service<PointEntity> {
    private static final DAO<PointEntity> pointDAO = new PointDao();

    @Override
    public PointEntity create(PointEntity entity) {
        return pointDAO.create(entity);
    }

    @Override
    public PointEntity read(String query) {
        return pointDAO.read(query);
    }

    @Override
    public PointEntity update(PointEntity entity) {
        return pointDAO.update(entity);
    }

    @Override
    public void delete(PointEntity entity) {
        pointDAO.delete(entity);
    }
}
