package Labs_OOP_sem_3.dto;

import Labs_OOP_sem_3.entities.FunctionEntity;
import lombok.Data;

@Data
public class PointDto
{
    private int id;
    private FunctionEntity function;
    private double x;
    private double y;
}