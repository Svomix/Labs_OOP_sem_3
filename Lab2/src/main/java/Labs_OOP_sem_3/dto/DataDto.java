package Labs_OOP_sem_3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DataDto
{
    private ArrayList<Double> arrX;
    private ArrayList<Double> arrY;
    private String type;
}
