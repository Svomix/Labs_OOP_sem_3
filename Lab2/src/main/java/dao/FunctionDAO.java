package dao;

import entities.FunctionEntity;

public interface FunctionDAO {
    FunctionEntity create(FunctionEntity entity);

    FunctionEntity read(String query);

    FunctionEntity update(FunctionEntity entity);

    void delete(FunctionEntity entity);

}
