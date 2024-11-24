package Labs_OOP_sem_3.service;

import Labs_OOP_sem_3.dto.PointDto;
import Labs_OOP_sem_3.entities.PointEntity;
import Labs_OOP_sem_3.repositories.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static Labs_OOP_sem_3.convertos.ConvertorToPointEntity.convertToEntity;

@org.springframework.stereotype.Service
@AllArgsConstructor
@Transactional
public class PointService {
    private final PointRepository repository;

    public PointEntity create(PointDto pointDto) {
        return repository.save(convertToEntity(pointDto));
    }

    public PointEntity read(int id) {
        return repository.getById(id);
    }

    public PointEntity update(PointDto pointDto) {
        return repository.save(convertToEntity(pointDto));
    }

    public void delete(PointDto pointDto) {
        repository.delete(convertToEntity(pointDto));
    }

    public ArrayList<PointEntity> findByFunc(int funcId) {
        return repository.findByFunction(funcId);
    }
    public void updateSequence()
    {
       repository.restartSeq();
    }
}