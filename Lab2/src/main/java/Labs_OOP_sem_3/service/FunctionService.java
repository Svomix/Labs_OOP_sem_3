package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dto.FunctionDto;
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

    public FunctionEntity create(FunctionDto funcDto) {
        FunctionEntity funcEntity = functionRepository.findByName(funcDto.getName());
        if (funcEntity == null) {
            funcDto.setName("" + HashUtil.hash(funcDto.getPoints()));
            var func = functionRepository.save(convert(funcDto));
            var arrP = new ArrayList<PointEntity>();
            for (var p : funcDto.getPoints()) {
                p.setFunction(func);
                arrP.add(pointRepository.save(p));
            }
            return func;
        }
        return funcEntity;
    }

    public void update(FunctionDto funcDto) {
        FunctionEntity function = functionRepository.findByName(funcDto.getName());
        pointRepository.deleteAll(pointRepository.findByFunction(function.getId()));
        funcDto.setId(function.getId());
        funcDto.setName("" + HashUtil.hash(funcDto.getPoints()));
        var func = functionRepository.save(convert(funcDto));
        var arrP = new ArrayList<PointEntity>();
        for (var p : funcDto.getPoints()) {
            p.setFunction(func);
            arrP.add(pointRepository.save(p));
        }
    }

    public void delete(FunctionDto funcDto) {
        FunctionEntity function = functionRepository.findByName(funcDto.getName());
        functionRepository.delete(function);
    }


    public FunctionEntity read(Integer id) {
        return functionRepository.findById(id).orElse(null);
    }




    public FunctionEntity readByName(String name) {
        return functionRepository.findByName(name);
    }

    public void updateSequence() {
        functionRepository.restartSeq();
    }
}
