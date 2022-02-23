package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.edu.project.backend.da.jpa.entity.RequestEntity;

import java.util.List;

@Repository
public interface RequestEntityRepository extends PagingAndSortingRepository<RequestEntity, Long>, JpaSpecificationExecutor<RequestEntity> {

    /**
     * Поиск записей по полю client_id.
     *
     * @param clientId
     * @return list entity
     */
    List<RequestEntity> findByClientId(Long clientId);
}
