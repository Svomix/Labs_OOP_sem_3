package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.convertos.ConvertorToPointEntity;
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
    public ResponseEntity<PointEntity> create(@RequestBody PointDto pointDto) {
        var point = pointService.create(pointDto);
        return ResponseEntity.ok(point);
    }

    /*
    @GetMapping("/id")
    public ResponseEntity<PointEntity> read(@RequestParam int id) {
        PointEntity pointEntity = pointService.read(id);
        return ResponseEntity.ok(pointEntity);
    }

     */


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
