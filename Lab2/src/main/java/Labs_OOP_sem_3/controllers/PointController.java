package Labs_OOP_sem_3.controllers;


import Labs_OOP_sem_3.concurrent.Integrate;
import Labs_OOP_sem_3.convertos.ConvertToFuncDto;
import Labs_OOP_sem_3.convertos.ConvertorToCompEntity;
import Labs_OOP_sem_3.convertos.ConvertorToFuncEntity;
import Labs_OOP_sem_3.convertos.ConvertorToPointEntity;
import Labs_OOP_sem_3.dto.*;
import Labs_OOP_sem_3.entities.CompFuncEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.functions.*;
import Labs_OOP_sem_3.functions.factory.LinkedListTabulatedFunctionFactory;
import Labs_OOP_sem_3.operations.TabulatedDifferentialOperator;
import Labs_OOP_sem_3.repositories.CompRepository;
import Labs_OOP_sem_3.repositories.UserRepository;
import Labs_OOP_sem_3.service.FunctionService;
import Labs_OOP_sem_3.service.PointService;
import Labs_OOP_sem_3.service.ReflectionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static Labs_OOP_sem_3.convertos.ConvertorPointToPointEntity.convert;
import static Labs_OOP_sem_3.operations.TabulatedFunctionOperationService.asPoint;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointController {
    private final PointService pointService;
    private final FunctionService functionService;
    private final ReflectionService reflectionService;
    CompRepository compRepository;
    UserRepository repository;

    @PostMapping("/table")
    public ResponseEntity<FunctionDtoList> createArr(@RequestBody DataDtoTable data, @RequestParam String userName) {
        var user = repository.findByUsername(userName);
        FunctionDtoList func = FunctionDtoList.builder().points(new ArrayList<>()).id_user(user.get().getId()).type(data.getType()).build();
        for (int i = 0; i < data.getArrX().size(); ++i) {
            func.getPoints().add(ConvertorToPointEntity.convertToEntity(PointDto.builder().x(data.getArrX().get(i)).y(data.getArrY().get(i)).function(ConvertorToFuncEntity.convert(func)).build()));
        }
        var f = functionService.create(func);
        return ResponseEntity.ok(ConvertToFuncDto.convert(f));
    }

    @PostMapping("/interval")
    public ResponseEntity<FunctionDtoList> createInterval(@RequestBody DataDtoInterval data, @RequestParam String userName) {
        var user = repository.findByUsername(userName);
        FunctionDtoList func = FunctionDtoList.builder().id_user(user.get().getId()).points(new ArrayList<>()).type(data.getTypeFabric()).build();
        MathFunction f = reflectionService.create(data.getTypeFunc());
        var func1 = new LinkedListTabulatedFunction(f, data.getStart(), data.getEnd(), data.getNumberOfPoints());
        for (var p : asPoint(func1))
            func.getPoints().add(convert(p));
        var f1 = functionService.create(func);
        return ResponseEntity.ok(ConvertToFuncDto.convert(f1));
    }

    @PostMapping("/comp_table")
    public ResponseEntity<FunctionDtoList> createTable(@RequestBody DataDtoTable data, @RequestParam String userName) {
        var user = repository.findByUsername(userName);
        FunctionDtoList func = FunctionDtoList.builder().points(new ArrayList<>()).name(data.getName()).composite(data.getComposite()).id_user(user.get().getId()).type(data.getType()).build();
        for (int i = 0; i < data.getArrX().size(); ++i) {
            func.getPoints().add(ConvertorToPointEntity.convertToEntity(PointDto.builder().x(data.getArrX().get(i)).y(data.getArrY().get(i)).function(ConvertorToFuncEntity.convert(func)).build()));
        }
        var f = functionService.create(func);
        return ResponseEntity.ok(ConvertToFuncDto.convert(f));
    }

    @PostMapping("/comp_create")
    public ResponseEntity<CompFuncEntity> createCompTable(@RequestBody ArrayList<String> names, @RequestParam String userName, @RequestParam String name) {
        var user = repository.findByUsername(userName);
        var comp = CompDto.builder().name(name).id_user(user.get().getId()).build();
        var sComp = compRepository.save(ConvertorToCompEntity.convert(comp));
        int pr = 1;
        for (var nameF : names) {
            var f = functionService.readByName(nameF);
            if (f == null) {
                var compF = compRepository.findByName(nameF);
                if (compF == null)
                    return ResponseEntity.badRequest().body(null);
                var fArr = functionService.readByIdComp(compF.getId());
                for (var f1: fArr)
                {
                    f1.setId_comp(sComp.getId());
                    functionService.create(ConvertToFuncDto.convert(f1));
                }
                break;
            }
            var arrF = pointService.findByFunc(f.getId());
            FunctionDtoList func = FunctionDtoList.builder().points(new ArrayList<>()).id_comp(sComp.getId()).composite(Integer.toString(pr)).id_user(user.get().getId()).type(f.getType()).build();
            for (int i = 0; i < arrF.size(); ++i) {
                func.getPoints().add(ConvertorToPointEntity.convertToEntity(PointDto.builder().x(arrF.get(i).getXValue()).y(arrF.get(i).getYValue()).function(ConvertorToFuncEntity.convert(func)).build()));
            }
            functionService.create(func);
        }
        return ResponseEntity.ok(sComp);
        // var funcArr = new ArrayList<LinkedListTabulatedFunction>();
//        for (var fn: data.getFuncArr()) {
//            var func = new LinkedListTabulatedFunction(fn.getArrX().stream().mapToDouble(Double::doubleValue).toArray(), fn.getArrY().stream().mapToDouble(Double::doubleValue).toArray());
//            funcArr.add(func);
//        }
//        var res = new CompositeFunction(funcArr.get(0),funcArr.get(1));
//        for (int i = 2; i < funcArr.size(); ++i) {
//            res = new CompositeFunction(res, funcArr.get(i));
//        }
//        var cRes = CompositeFuncDto.builder().function(res).funcName(data.getName()).idUser(Math.toIntExact(user.get().getId())).build();
//        return ResponseEntity.ok(cRes);
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<FunctionDtoArr> handleFunction(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            // Определение типа файла по расширению
            String fileName = file.getOriginalFilename();
            boolean isXml = fileName != null && fileName.toLowerCase().endsWith(".xml");

            // Чтение файла и преобразование в список точек
            List<Point> points;
            if (isXml) {
                // Используем XmlMapper для XML
                XmlMapper xmlMapper = new XmlMapper();
                points = xmlMapper.readValue(file.getInputStream(), xmlMapper.getTypeFactory().constructCollectionType(List.class, Point.class));
            } else {
                // Используем ObjectMapper для JSON
                ObjectMapper objectMapper = new ObjectMapper();
                points = objectMapper.readValue(file.getInputStream(), objectMapper.getTypeFactory().constructCollectionType(List.class, Point.class));
            }

            if (points == null || points.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // Преобразование списка точек в FunctionDtoArr
            double[] x = new double[points.size()];
            double[] y = new double[points.size()];
            for (int i = 0; i < points.size(); ++i) {
                x[i] = points.get(i).x;
                y[i] = points.get(i).y;
            }

            FunctionDtoArr functionDtoArr = FunctionDtoArr.builder()
                    .x(x)
                    .y(y)
                    .type("ArrayTabulatedFunctionFactory")
                    .build();

            return ResponseEntity.ok(functionDtoArr);
        } catch (IOException e) {
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

    @GetMapping("/get_comp")
    public ResponseEntity<ArrayList<String>> getPartComp(@RequestParam String userName) {
        var user = repository.findByUsername(userName);
        var funcs = functionService.readAll(Math.toIntExact(user.get().getId()));
        var cNames = compRepository.findByIdUser(Math.toIntExact(user.get().getId()));
        var res = new ArrayList<String>();
        for (var name : cNames) {
            res.add(name);
        }
        if (funcs != null) {
            for (var f : funcs) {
                if (f.getComposite() != null) {
                    res.add(f.getName());
                }
            }
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
