package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.JobEntity;

import java.util.List;

@Repository
public interface JobEntityRepository extends CrudRepository<JobEntity, Long> {

    /**
     * Поиск записей по полю enabled.
     * @param enabled
     * @return list entity
     */
    List<JobEntity> findAllByEnabled(boolean enabled);
}
