package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.convertos.ConvertorToPointDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.service.FunctionService;
import Labs_OOP_sem_3.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToPointEntity.convertToEntity;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointController {
    private final PointService pointService;
    private final FunctionService functionService;

    @PostMapping
    public ResponseEntity<PointEntity> create(@RequestBody PointDto pointDto) {
        var point = pointService.create(pointDto);
        return ResponseEntity.ok(point);
    }

    @GetMapping("/id")
    public ResponseEntity<PointEntity> read(@RequestParam int id) {
        PointEntity pointEntity = pointService.read(id);
        return ResponseEntity.ok(pointEntity);
    }

    @PutMapping("/update")
    public ResponseEntity<PointEntity> update(@RequestBody PointDto pointDto) {
        var point = pointService.update(pointDto);
        return ResponseEntity.ok(point);
    }

    @DeleteMapping("/id")
    public ResponseEntity<PointEntity> delete(@RequestParam PointDto pointDto) {
        var id = pointDto.getId();
        if (pointService.read(id) != null) {
            pointService.delete(ConvertorToPointDto.convertToDto(pointService.read(id)));
            return ResponseEntity.ok(convertToEntity(pointDto));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/find")
    public ResponseEntity<ArrayList<PointEntity>> findByFunc(@RequestParam int funcId) {
        if (functionService.read(funcId) != null) {
            ArrayList<PointEntity> res = pointService.findByFunc(funcId);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
}
