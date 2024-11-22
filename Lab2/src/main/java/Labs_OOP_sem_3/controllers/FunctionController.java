package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.service.FunctionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/functions")
public class FunctionController {
    private final FunctionService functionService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody FunctionDto functionDto) {
        functionService.create(functionDto);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionEntity> get(@PathVariable Integer id) {
        FunctionEntity functionEntity = functionService.read(id);
        return functionEntity != null ? ResponseEntity.ok(functionEntity) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{userName}")
    public ResponseEntity<FunctionEntity> getByUserName(@PathVariable String userName) {
        FunctionEntity functionEntity = functionService.readByName(userName);
        return functionEntity != null ? ResponseEntity.ok(functionEntity) : ResponseEntity.notFound().build();
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
