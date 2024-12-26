package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.CompDto;
import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.CompFuncEntity;
import Labs_OOP_sem_3.entities.FunctionEntity;

public class ConvertorToCompEntity {
    public static CompFuncEntity convert(CompDto compDto) {
        return CompFuncEntity.builder().name(compDto.getName()).idUser(Math.toIntExact(compDto.getId_user())).build();
    }
}