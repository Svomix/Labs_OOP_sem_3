package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.FunctionEntity;

public class ConvertToFuncDto {
    public static FunctionDtoList convert(FunctionEntity function) {
        FunctionDtoList functionDtoList = FunctionDtoList.builder().hash(function.getHash()).id_user((long) function.getId_user()).name(function.getName()).composite(function.getComposite()).type(function.getType()).id(function.getId()).build();
        return functionDtoList;
    }
}
