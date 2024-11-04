package service;

import entities.FunctionEntity;

public interface FunctionService {
    FunctionEntity create(FunctionEntity entity);
    FunctionEntity read(String query);
    FunctionEntity update(FunctionEntity entity);
    void delete(FunctionEntity entity);

}
