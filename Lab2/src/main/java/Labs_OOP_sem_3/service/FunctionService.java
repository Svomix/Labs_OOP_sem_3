package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import Labs_OOP_sem_3.repositories.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToFuncEntity.convert;

@Service
@AllArgsConstructor
@Transactional
public class FunctionService {
    private final FunctionRepository functionRepository;
    private final PointRepository pointRepository;
    public FunctionEntity create(FunctionDto funcDto) {
       return(functionRepository.save(convert(funcDto)));
    }

    public void update(FunctionDto funcDto) {
        var func = functionRepository.save(convert(funcDto));
        var arrP = new ArrayList<PointEntity>();
        for (var p: funcDto.getPoints()) {
            p.setFunction(func);
            arrP.add(pointRepository.save(p));
        }
        pointRepository.saveAll(arrP);
    }

    public void delete(FunctionDto funcDto) {
        functionRepository.delete(convert(funcDto));
    }

    public FunctionEntity read(Integer id) {
        return functionRepository.findById(id).orElse(null);
    }
    public FunctionEntity readByName(String name) {
        return functionRepository.findByName(name);
    }
    public void updateSequence()
    {
        functionRepository.restartSeq();
    }
}
