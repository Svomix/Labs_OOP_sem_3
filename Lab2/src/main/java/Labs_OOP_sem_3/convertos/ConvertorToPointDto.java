package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.PointEntity;

public class ConvertorToPointDto {
    static public PointDto convertToDto(PointEntity point) {
        PointDto pointDto = new PointDto();
        pointDto.setId(point.getId());
        pointDto.setY(point.getYValue());
        pointDto.setX(point.getXValue());
        pointDto.setFunction(point.getFunction());
        return pointDto;
    }
}
