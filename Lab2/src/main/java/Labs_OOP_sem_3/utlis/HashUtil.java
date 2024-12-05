package Labs_OOP_sem_3.utlis;

import Labs_OOP_sem_3.entities.PointEntity;

import java.util.ArrayList;

public class HashUtil {
    public static int hash(ArrayList<PointEntity> points) {
        if (points.isEmpty()) return 0;
        StringBuilder pointsString = new StringBuilder();
        for (PointEntity point : points) {
            pointsString.append("{").append(point.getXValue()).append(", ").append(point.getYValue()).append("}").append(", ");
        }
        return pointsString.toString().hashCode();
    }
}
