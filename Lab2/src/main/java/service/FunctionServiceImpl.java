package service;

import dao.DAO;
import dao.FunctionDao;
import entities.FunctionEntity;


public class FunctionServiceImpl implements Service<FunctionEntity> {
    private static final DAO<FunctionEntity> functionDAO = new FunctionDao();

    /*
    public void setFunctionDAO(FunctionDAO functionDAO) {
        this.functionDAO = functionDAO;
    }

     */

    @Override
    public FunctionEntity create(FunctionEntity entity) {
        return functionDAO.create(entity);
    }

    @Override
    public FunctionEntity read(String query) {
        return functionDAO.read(query);
    }

    @Override
    public FunctionEntity update(FunctionEntity entity) {
        return functionDAO.update(entity);
    }

    @Override
    public void delete(FunctionEntity entity) {
        functionDAO.delete(entity);
    }
}
