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
    private Integer id;
    private String type;
    private String hash;
    private Long id_user;
    private int id_comp;
    private String name;
    private String composite;
    private List<PointEntity> points;
}
