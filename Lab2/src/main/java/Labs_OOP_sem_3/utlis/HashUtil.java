package Labs_OOP_sem_3.utlis;

import Labs_OOP_sem_3.entities.PointEntity;

import java.util.ArrayList;
import java.util.List;

public class HashUtil {
    public static int hash(List<PointEntity> points) {
        if (points == null) return 0;
        StringBuilder pointsString = new StringBuilder();
        for (PointEntity point : points) {
            pointsString.append("{").append(point.getXValue()).append(", ").append(point.getYValue()).append("}").append(", ");
        }
        return pointsString.toString().hashCode();
    }
}
