package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dao.DAO;
import Labs_OOP_sem_3.dao.PointDao;
import Labs_OOP_sem_3.entities.PointEntity;

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
