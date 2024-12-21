package Labs_OOP_sem_3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DataDtoInterval
{
    int start;
    int end;
    int numberOfPoints;
    String typeFabric;
    String typeFunc;
}