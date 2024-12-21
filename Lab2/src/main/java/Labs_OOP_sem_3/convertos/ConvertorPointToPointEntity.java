package Labs_OOP_sem_3.convertos;

import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.functions.Point;

public class ConvertorPointToPointEntity
{
        public static PointEntity convert(Point point) {
            return PointEntity.builder().yValue(point.y).xValue(point.x).build();
    }
}
