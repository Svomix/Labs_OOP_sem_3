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
public class DataDtoComposite
{
    private ArrayList<DataDtoTable> funcArr;
    private String composite;
    private String name;
}
