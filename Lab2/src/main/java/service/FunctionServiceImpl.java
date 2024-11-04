package service;

import dao.FunctionDAO;
import entities.FunctionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FunctionServiceImpl implements FunctionService {
    private FunctionDAO functionDAO;

    @Autowired
    public void setFunctionDAO(FunctionDAO functionDAO) {
        this.functionDAO = functionDAO;
    }

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
