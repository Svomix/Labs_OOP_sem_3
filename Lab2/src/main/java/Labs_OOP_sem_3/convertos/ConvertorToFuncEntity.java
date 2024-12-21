package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.FunctionEntity;

public class ConvertorToFuncEntity {
    public static FunctionEntity convert(FunctionDtoList functionDtoList) {
        FunctionEntity functionEntity = FunctionEntity.builder().type(functionDtoList.getType()).
                id(functionDtoList.getId()).
                hash(functionDtoList.getHash()).
                build();
        return functionEntity;
    }
}
