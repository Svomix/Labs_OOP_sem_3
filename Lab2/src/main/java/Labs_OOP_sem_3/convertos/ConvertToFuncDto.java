package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;

public class ConvertToFuncDto {
    public static FunctionDto convert(FunctionEntity function) {
        FunctionDto functionDto = FunctionDto.builder().hash(function.getHash()).id(function.getId()).build();
        return functionDto;
    }
}
