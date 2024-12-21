package Labs_OOP_sem_3.dto;

import Labs_OOP_sem_3.entities.PointEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class FunctionDtoList {
    Integer id;
    String type;
    String hash;
    private List<PointEntity> points;
}
