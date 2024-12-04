package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.convertos.ConvertToFuncDto;
import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.PointRepository;
import Labs_OOP_sem_3.utlis.HashUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToPointEntity.convertToEntity;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class PointService {
    private final PointRepository repository;
    private final FunctionService functionService;

    public PointEntity create(PointDto pointDto) {
        FunctionEntity func = functionService.readByName(pointDto.getFunction().getName());
        pointDto.getFunction().setId(func.getId());
        PointEntity point = repository.save(convertToEntity(pointDto));
        FunctionDto functionDto = ConvertToFuncDto.convert(func);
        functionDto.setPoints(repository.findByFunction(func.getId()));
        functionService.update(functionDto);
        return point;
    }

    public PointEntity read(int id) {
        return repository.findById(id).orElse(null);
    }

    public PointEntity update(PointDto pointDto) {
        FunctionEntity func = functionService.readByName(pointDto.getFunction().getName());
        PointEntity point = repository.findByFunctionIdAndPoint(func.getId(), pointDto.getX());
        point.setYValue(pointDto.getY());
        repository.save(point);
        FunctionDto functionDto = ConvertToFuncDto.convert(func);
        functionDto.setPoints(repository.findByFunction(functionDto.getId()));
        functionService.update(functionDto);
        return point;
    }



    public void delete(PointDto pointDto) {
        FunctionEntity func = functionService.readByName(pointDto.getFunction().getName());
        PointEntity point = repository.findByFunctionIdAndPoint(func.getId(), pointDto.getX());
        repository.deleteById(point.getId());
        FunctionDto functionDto = ConvertToFuncDto.convert(func);
        functionDto.setPoints(repository.findByFunction(functionDto.getId()));
        functionService.update(functionDto);
    }

    public ArrayList<PointEntity> findByFunc(int funcId) {

        var arrPoint = repository.findByFunction(funcId);
        if (arrPoint.isEmpty())
            return null;
        return arrPoint;
    }

    public void updateSequence() {
        repository.restartSeq();
    }
}