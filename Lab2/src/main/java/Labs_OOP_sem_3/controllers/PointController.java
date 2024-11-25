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

@RestController
@RequestMapping("/point")
@AllArgsConstructor
public class        PointController {
    private final PointService pointService;
    private final FunctionService functionService;

    @PostMapping
    public ResponseEntity<PointEntity> create(@RequestBody PointDto pointDto) {
       var point =  pointService.create(pointDto);
        return ResponseEntity.ok(point);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PointEntity> read(@PathVariable int id) {
        PointEntity pointEntity = pointService.read(id);
        return ResponseEntity.ok(pointEntity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PointEntity> update(@PathVariable int id, @RequestBody PointDto pointDto) {
        pointDto.setId(id);
        var point = pointService.update(pointDto);
        return ResponseEntity.ok(point);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (pointService.read(id) != null) {
            pointService.delete(ConvertorToPointDto.convertToDto(pointService.read(id)));
            return ResponseEntity.ok("Point deleted");
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
