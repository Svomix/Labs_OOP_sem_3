package Labs_OOP_sem_3.dto;

import Labs_OOP_sem_3.functions.CompositeFunction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CompositeFuncDto {
    private String funcName;
    private CompositeFunction function;
    private int idUser;
}
