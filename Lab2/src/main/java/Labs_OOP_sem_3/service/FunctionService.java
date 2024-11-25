package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import lombok.AllArgsConstructor;
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

    public FunctionEntity create(FunctionDto funcDto) {
       return(functionRepository.save(convert(funcDto)));
    }

    public void update(FunctionDto funcDto) {
        functionRepository.save(convert(funcDto));
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
