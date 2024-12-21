package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.convertos.ConvertToFuncDto;
import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.PointRepository;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToPointEntity.convertToEntity;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class PointService {
    private final PointRepository repository;
    private final FunctionService functionService;

    public PointEntity create(PointDto pointDto) {
        FunctionEntity func = functionService.readByName(pointDto.getFunction().getHash());
        pointDto.getFunction().setId(func.getId());
        PointEntity point = repository.save(convertToEntity(pointDto));
        FunctionDtoList functionDtoList = ConvertToFuncDto.convert(func);
        functionDtoList.setPoints(repository.findByFunction(func.getId()));
        functionService.update(functionDtoList);
        return point;
    }

    public PointEntity readByFuncIdAndPoint(Integer id, Double x) {
        return repository.findByFunctionIdAndPoint(id, x);
    }

    public PointEntity update(PointDto pointDto) {
        FunctionEntity func = functionService.readByName(pointDto.getFunction().getHash());
        PointEntity point = repository.findByFunctionIdAndPoint(func.getId(), pointDto.getX());
        if (point != null) {
            point.setYValue(pointDto.getY());
            repository.save(point);
            FunctionDtoList functionDtoList = ConvertToFuncDto.convert(func);
            functionDtoList.setPoints(repository.findByFunction(functionDtoList.getId()));
            functionService.update(functionDtoList);
        }
        return point;
    }


    public void delete(PointDto pointDto) {
        FunctionEntity func = functionService.readByName(pointDto.getFunction().getHash());
        PointEntity point = repository.findByFunctionIdAndPoint(func.getId(), pointDto.getX());
        if (point.getId() != null) {
            repository.deleteById(point.getId());
        }
        FunctionDtoList functionDtoList = ConvertToFuncDto.convert(func);
        functionDtoList.setPoints(repository.findByFunction(functionDtoList.getId()));
        functionService.update(functionDtoList);
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