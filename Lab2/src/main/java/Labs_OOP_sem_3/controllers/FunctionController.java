package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.service.FunctionService;
import Labs_OOP_sem_3.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/functions")
public class FunctionController {
    private final FunctionService functionService;
    private final PointService pointService;

    @PostMapping
    public ResponseEntity<FunctionEntity> create(@RequestBody FunctionDto functionDto) {
        var funcEnt = functionService.create(functionDto);
        return ResponseEntity.ok(funcEnt);
    }

    @GetMapping("/name")
    public ResponseEntity<FunctionDto> getByName(@RequestParam String name) {
        var func = functionService.readByName(name);
        if (func != null) {
            var funcDto = FunctionDto.builder().id(func.getId()).hash(func.getHash()).points(pointService.findByFunc(func.getId())).build();
            return ResponseEntity.ok(funcDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<FunctionEntity> update(@RequestBody FunctionDto functionDto) {
        FunctionEntity functionEntity = functionService.readByName(functionDto.getHash());
        if (functionEntity != null) {
            functionService.update(functionDto);
            return ResponseEntity.ok(functionEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<FunctionEntity> delete(@RequestBody FunctionDto functionDto) {
        FunctionEntity functionEntity = functionService.readByName(functionDto.getHash());
        if (functionEntity != null) {
            functionService.delete(functionDto);
            return ResponseEntity.ok(functionEntity);
        }
        return ResponseEntity.notFound().build();
    }
}
