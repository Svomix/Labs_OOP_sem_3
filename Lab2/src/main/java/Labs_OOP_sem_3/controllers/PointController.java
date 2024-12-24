package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.concurrent.Integrate;
import Labs_OOP_sem_3.convertos.ConvertToFuncDto;
import Labs_OOP_sem_3.convertos.ConvertorToFuncEntity;
import Labs_OOP_sem_3.convertos.ConvertorToPointEntity;
import Labs_OOP_sem_3.dto.DataDtoInterval;
import Labs_OOP_sem_3.dto.DataDtoTable;
import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.functions.*;
import Labs_OOP_sem_3.functions.factory.LinkedListTabulatedFunctionFactory;
import Labs_OOP_sem_3.operations.TabulatedDifferentialOperator;
import Labs_OOP_sem_3.service.FunctionService;
import Labs_OOP_sem_3.service.PointService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static Labs_OOP_sem_3.convertos.ConvertorPointToPointEntity.convert;
import static Labs_OOP_sem_3.operations.TabulatedFunctionOperationService.asPoint;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointController {
    private final PointService pointService;
    private final FunctionService functionService;

    @PostMapping("/table")
    public ResponseEntity<FunctionDtoList> createArr(@RequestBody DataDtoTable data) {
        FunctionDtoList func = FunctionDtoList.builder().points(new ArrayList<>()).type(data.getType()).build();
        for (int i = 0; i < data.getArrX().size(); ++i) {
            func.getPoints().add(ConvertorToPointEntity.convertToEntity(PointDto.builder().x(data.getArrX().get(i)).y(data.getArrY().get(i)).function(ConvertorToFuncEntity.convert(func)).build()));
        }
        var f = functionService.create(func);
        return ResponseEntity.ok(ConvertToFuncDto.convert(f));
    }

    @PostMapping("/interval")
    public ResponseEntity<FunctionDtoList> createInterval(@RequestBody DataDtoInterval data) {
        FunctionDtoList func = FunctionDtoList.builder().points(new ArrayList<>()).type(data.getTypeFabric()).build();
        switch (data.getTypeFunc()) {
            case "UnitFunction":
                var func1 = new LinkedListTabulatedFunction(new UnitFunction(), data.getStart(), data.getEnd(), data.getNumberOfPoints());
                for (var p : asPoint(func1))
                    func.getPoints().add(convert(p));
                var f1 = functionService.create(func);
                return ResponseEntity.ok(ConvertToFuncDto.convert(f1));
            case "SqrFunction":
                var func2 = new LinkedListTabulatedFunction(new SqrFunction(), data.getStart(), data.getEnd(), data.getNumberOfPoints());
                for (var p : asPoint(func2))
                    func.getPoints().add(convert(p));
                var f2 = functionService.create(func);
                return ResponseEntity.ok(ConvertToFuncDto.convert(f2));
            case "ZeroFunction":
                var func3 = new LinkedListTabulatedFunction(new ZeroFunction(), data.getStart(), data.getEnd(), data.getNumberOfPoints());
                for (var p : asPoint(func3))
                    func.getPoints().add(convert(p));
                var f3 = functionService.create(func);
                return ResponseEntity.ok(ConvertToFuncDto.convert(f3));
            case "IdentityFunction":
                var func4 = new LinkedListTabulatedFunction(new IdentityFunction(), data.getStart(), data.getEnd(), data.getNumberOfPoints());
                for (var p : asPoint(func4))
                    func.getPoints().add(convert(p));
                var f4 = functionService.create(func);
                return ResponseEntity.ok(ConvertToFuncDto.convert(f4));
            default:
                return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/diff")
    public ResponseEntity<FunctionDtoList> performDiff(@RequestBody DataDtoTable data) {
        if (data != null) {
            var a = new TabulatedDifferentialOperator(new LinkedListTabulatedFunctionFactory());
            double[] x = new double[data.getArrX().size()];
            double[] y = new double[data.getArrY().size()];
            for (int i = 0; i < data.getArrX().size(); ++i) {
                x[i] = data.getArrX().get(i);
                y[i] = data.getArrY().get(i);
            }
            var res = a.deriveSynchronously(new LinkedListTabulatedFunction(x, y));
            var f = FunctionDtoList.builder().points(new ArrayList<>()).type(data.getType()).build();
            for (var p : asPoint(res)) {
                f.getPoints().add(convert(p));
            }
            return ResponseEntity.ok(f);
        }
        return ResponseEntity.status(500).build();
    }

//    @PostMapping("/file")
//    public ResponseEntity<FunctionDtoList> saveFile(@RequestBody DataDtoTable data, @RequestParam String filename) {
//
//    }

    @PostMapping("integr")
    public ResponseEntity<Double> performIntegr(@RequestBody DataDtoTable data, @RequestParam int th) throws ExecutionException, InterruptedException {
        if (data != null) {
            double[] x = new double[data.getArrX().size()];
            double[] y = new double[data.getArrY().size()];
            for (int i = 0; i < data.getArrX().size(); ++i) {
                x[i] = data.getArrX().get(i);
                y[i] = data.getArrY().get(i);
            }
            TabulatedFunction func;
            if (data.getType().equals("ArrayTabulatedFunctionFactory")) {
                func = new ArrayTabulatedFunction(x, y);
            } else {
                func = new LinkedListTabulatedFunction(x, y);
            }
            Integrate operator = new Integrate(th);
            return ResponseEntity.ok(operator.integrate(func, 30));
        }
        return ResponseEntity.status(500).build();
    }

    @PostMapping("apply")
    public ResponseEntity<Double> apply(@RequestBody DataDtoTable data, @RequestParam double xVal) {
        if (data != null) {
            double[] x = new double[data.getArrX().size()];
            double[] y = new double[data.getArrY().size()];
            for (int i = 0; i < data.getArrX().size(); ++i) {
                x[i] = data.getArrX().get(i);
                y[i] = data.getArrY().get(i);
            }
            MathFunction func;
            if (data.getType().equals("ArrayTabulatedFunctionFactory")) {
                func = new ArrayTabulatedFunction(x, y);
            } else {
                func = new LinkedListTabulatedFunction(x, y);
            }
            var res = func.apply(xVal);
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.status(500).build();
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

    @GetMapping
    public ResponseEntity<ArrayList<PointEntity>> findByFunc(@RequestParam String hash) {
        var func = functionService.readByName(hash);
        if (func != null) {
            ArrayList<PointEntity> res = pointService.findByFunc(func.getId());
            return ResponseEntity.ok(res);
        }
        return ResponseEntity.notFound().build();
    }
}
