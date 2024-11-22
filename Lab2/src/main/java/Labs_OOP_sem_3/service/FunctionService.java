package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dto.FunctionDto;
import Labs_OOP_sem_3.entities.FunctionEntity;
import Labs_OOP_sem_3.repositories.FunctionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class FunctionService {
    private final FunctionRepository functionRepository;

    public void create(FunctionDto funcDto) {
        functionRepository.save(convert(funcDto));
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

    public FunctionEntity convert(FunctionDto functionDto) {
        FunctionEntity functionEntity = new FunctionEntity();
        functionEntity.setId(functionDto.getId());
        functionEntity.setName(functionDto.getName());
        functionEntity.setPoints(functionDto.getPoints());
        return functionEntity;
    }
}
