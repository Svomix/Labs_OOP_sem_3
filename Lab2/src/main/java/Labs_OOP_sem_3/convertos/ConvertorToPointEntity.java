package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.PointEntity;

public class ConvertorToPointEntity {
    static public PointEntity convertToEntity(PointDto PointDto) {
        PointEntity pointEntity = new PointEntity();
        pointEntity.setId(PointDto.getId());
        pointEntity.setYValue(PointDto.getY());
        pointEntity.setXValue(PointDto.getX());
        pointEntity.setFunction(PointDto.getFunction());
        return pointEntity;
    }
}
