package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;

public class ConvertorToFuncEntity {
    public static FunctionEntity convert(FunctionDto functionDto) {
        FunctionEntity functionEntity = FunctionEntity.builder().
                id(functionDto.getId()).
                hash(functionDto.getHash()).build();
        return functionEntity;
    }
}
