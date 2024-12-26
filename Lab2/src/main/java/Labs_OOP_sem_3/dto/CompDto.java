package Labs_OOP_sem_3.dto;

import Labs_OOP_sem_3.entities.FunctionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CompDto {
    private int id;
    private String name;
    private Long id_user;
}