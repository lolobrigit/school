package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskEntityRepository extends CrudRepository<TaskEntity, UUID> {


    /**
     * поиск по клиенту.
     * @param clientId
     * @return список
     */
    List<TaskEntity> findAllByClientId(long clientId);

}
