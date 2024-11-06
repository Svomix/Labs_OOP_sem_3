package dao;

import entities.FunctionEntity;
import entities.Modification_type;

public class MainDao {
    public static void main(String[] args) {
            FunctionDaoImpl functionDaoImpl = new FunctionDaoImpl();
            FunctionEntity functionEntity = FunctionEntity.builder()
                    .mod(Modification_type.usual)
                    .hash("545521")
                    .xArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0})
                    .yArr(new Double[]{1.0, 2.0, 3.0, 4.0, 5.0, 7.0})
                    .build();
            functionDaoImpl.create(functionEntity);
            functionDaoImpl.delete(functionEntity);
    }
}
