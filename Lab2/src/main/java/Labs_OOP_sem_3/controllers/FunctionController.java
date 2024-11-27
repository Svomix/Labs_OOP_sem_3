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

    @GetMapping("/id")
    public ResponseEntity<FunctionEntity> get(@RequestParam int id) {
        FunctionEntity functionEntity = functionService.read(id);
        return functionEntity != null ? ResponseEntity.ok(functionEntity) : ResponseEntity.notFound().build();
    }

    @GetMapping("/name")
    public ResponseEntity<FunctionDto> getByName(@RequestParam String name) {
        var func = functionService.readByName(name);
        if (func != null) {
            var funcDto = FunctionDto.builder().id(func.getId()).name(func.getName()).points(pointService.findByFunc(func.getId())).build();
            return ResponseEntity.ok(funcDto);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/funcId")
    public ResponseEntity<FunctionDto> getById(@RequestParam int funcId) {
        var func = functionService.read(funcId);
        if (func != null) {
            var getFunc = FunctionDto.builder().id(funcId).name(func.getName()).points(pointService.findByFunc(funcId)).build();
            return ResponseEntity.ok(getFunc);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<FunctionEntity> update(@RequestBody FunctionDto functionDto) {
        FunctionEntity functionEntity = functionService.read(functionDto.getId());
        if (functionEntity != null) {
            functionService.update(functionDto);
            return ResponseEntity.ok(functionEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<FunctionEntity> delete(@RequestBody FunctionDto functionDto) {
        FunctionEntity functionEntity = functionService.read(functionDto.getId());
        if (functionEntity != null) {
            functionService.delete(functionDto);
            return ResponseEntity.ok(functionEntity);
        }
        return ResponseEntity.notFound().build();
    }
}
