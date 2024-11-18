package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dao.DAO;
import Labs_OOP_sem_3.dao.FunctionDao;
import Labs_OOP_sem_3.entities.FunctionEntity;
import org.springframework.transaction.annotation.Transactional;


public class FunctionServiceImpl implements Service<FunctionEntity> {
    private static final DAO<FunctionEntity> functionDAO = new FunctionDao();

    /*
    public void setFunctionDAO(FunctionDAO functionDAO) {
        this.functionDAO = functionDAO;
    }

     */

    @Override
    @Transactional
    public FunctionEntity create(FunctionEntity entity) {
        return functionDAO.create(entity);
    }

    @Override
    @Transactional
    public FunctionEntity read(String query) {
        return functionDAO.read(query);
    }

    @Override
    @Transactional
    public FunctionEntity update(FunctionEntity entity) {
        return functionDAO.update(entity);
    }

    @Override
    @Transactional
    public void delete(FunctionEntity entity) {
        functionDAO.delete(entity);
    }
}
