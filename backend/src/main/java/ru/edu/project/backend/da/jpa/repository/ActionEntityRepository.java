package ru.edu.project.backend.da.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import ru.edu.project.backend.da.jpa.entity.ActionEntity;

import java.util.List;

public interface ActionEntityRepository extends CrudRepository<ActionEntity, ActionEntity.ActionPk> {

    /**
     * Поиск действий по заявке.
     *
     * @param requestId
     * @return list
     */
    List<ActionEntity> findAllByPkRequestId(long requestId);
}
