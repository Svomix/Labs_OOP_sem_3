package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dto.FunctionDtoList;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import Labs_OOP_sem_3.repositories.PointRepository;
import Labs_OOP_sem_3.utlis.HashUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToFuncEntity.convert;

@Service
@AllArgsConstructor
public class FunctionService {
    private final FunctionRepository functionRepository;
    private final PointRepository pointRepository;

    public FunctionEntity create(FunctionDtoList funcDto) {
        FunctionEntity funcEntity = functionRepository.findByHash(funcDto.getHash());
        if (funcEntity == null || funcDto.getName() != null) {
            funcDto.setHash("" + HashUtil.hash(funcDto.getPoints()));
            var func = functionRepository.save(convert(funcDto));
            var arrP = funcDto.getPoints();
            if (arrP != null) {
                for (var p : funcDto.getPoints()) {
                    p.setFunction(func);
                    pointRepository.save(p);
                }
            }
            return func;
        }
        return funcEntity;
    }

    public void update(FunctionDtoList funcDto) {
        FunctionEntity function = functionRepository.findByHash(funcDto.getHash());
        ArrayList<PointEntity> arrP = pointRepository.findByFunction(function.getId());
        if (arrP != null) {
            pointRepository.deleteAll(arrP);
        }
        funcDto.setId(function.getId());
        funcDto.setHash("" + HashUtil.hash(funcDto.getPoints()));
        var func = functionRepository.save(convert(funcDto));
        ArrayList<PointEntity> pointEntities = new ArrayList<>();
        for (var p : funcDto.getPoints()) {
            PointEntity point = PointEntity.builder().id(p.getId()).function(func).xValue(p.getXValue()).yValue(p.getYValue()).build();
            pointRepository.save(point);
            pointEntities.add(point);
        }
        funcDto.setPoints(pointEntities);
    }

    public void delete(FunctionDtoList funcDto) {
        FunctionEntity function = functionRepository.findByHash(funcDto.getHash());
        if (function != null) {
            functionRepository.delete(function);
        }
    }


    public FunctionEntity read(Integer id) {
        return functionRepository.findById(id).orElse(null);
    }

    public ArrayList<FunctionEntity> readAll(Integer idUser) {
        return functionRepository.findByIdUser(idUser);
    }

    public FunctionEntity readByName(String name) {
        return functionRepository.findByName(name);
    }
    public FunctionEntity readByHash(String hash) {
        return functionRepository.findByHash(hash);
    }

    public ArrayList< FunctionEntity> readByIdComp(int id) {
        return functionRepository.findByIdComp(id);
    }
    public void updateSequence() {
        functionRepository.restartSeq();
    }
}
