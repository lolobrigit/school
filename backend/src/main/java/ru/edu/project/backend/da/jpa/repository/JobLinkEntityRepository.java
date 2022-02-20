package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.JobLinkEntity;

import java.util.List;

@Repository
public interface JobLinkEntityRepository extends CrudRepository<JobLinkEntity, JobLinkEntity.JobLinkId> {

    /**
     * Поиск записей по полю составного ключа pk.RequestId.
     *
     * @param requestId
     * @return list entity
     */
    List<JobLinkEntity> findAllByPkRequestId(long requestId);
}
