package service;

import dao.DAO;
import dao.FunctionDaoImpl;
import entities.FunctionEntity;
import org.springframework.transaction.annotation.Transactional;


public class FunctionServiceImpl implements FunctionService {
    private static final DAO<FunctionEntity> functionDAO = new FunctionDaoImpl();

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
