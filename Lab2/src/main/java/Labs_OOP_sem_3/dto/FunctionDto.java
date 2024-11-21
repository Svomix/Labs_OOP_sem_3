package Labs_OOP_sem_3.dto;

import Labs_OOP_sem_3.entities.PointEntity;
import lombok.Data;

import java.util.List;

@Data
public class FunctionDto {
    Integer id;
    String name;
    private List<PointEntity> points;
}
