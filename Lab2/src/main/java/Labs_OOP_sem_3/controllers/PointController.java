package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.convertos.ConvertToFuncDto;
import Labs_OOP_sem_3.convertos.ConvertorToFuncEntity;
import Labs_OOP_sem_3.convertos.ConvertorToPointDto;
import Labs_OOP_sem_3.convertos.ConvertorToPointEntity;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.dto.DataDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.service.FunctionService;
import Labs_OOP_sem_3.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointController {
    private final PointService pointService;
    private final FunctionService functionService;

    @PostMapping
    public ResponseEntity<FunctionDto> create(@RequestBody DataDto data) {
        FunctionDto func = FunctionDto.builder().build();
        for (int i = 0; i < data.getArrX().size(); ++i) {
             func.getPoints().add(ConvertorToPointEntity.convertToEntity(PointDto.builder().x(data.getArrX().get(i)).y(data.getArrY().get(i)).function(ConvertorToFuncEntity.convert(func)).build()));
        }
        if (data.getType() == null)
            func.setType("Tabulated");
        else
            func.setType(data.getType());
        var f = functionService.create(func);
        return ResponseEntity.ok(ConvertToFuncDto.convert(f));
    }

    @PutMapping("/update")
    public ResponseEntity<PointEntity> update(@RequestBody PointDto pointDto) {
        var point = pointService.update(pointDto);
        return ResponseEntity.ok(point);
    }

    @DeleteMapping
    public ResponseEntity<PointEntity> delete(@RequestBody PointDto pointDto) {
        pointService.delete(pointDto);
        return ResponseEntity.ok(ConvertorToPointEntity.convertToEntity(pointDto));
    }

    @GetMapping("/find")
    public ResponseEntity<ArrayList<PointEntity>> findByFunc(@RequestParam String name) {
        var func = functionService.readByName(name);
        if (func != null) {
            ArrayList<PointEntity> res = pointService.findByFunc(func.getId());
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
}
