package ru.edu.project.backend.da;

import ru.edu.project.backend.api.jobs.Job;

import java.util.List;

public interface JobDALayer {

    /**
     * Связывание запроса и услуги.
     *
     * @param requestId
     * @param jobId
     */
    void linkRequest(long requestId, long jobId);

    /**
     * Получение услуги по id.
     *
     * @param id
     * @return job
     */
    Job getById(Long id);

    /**
     * Получение списка услуг по ids.
     *
     * @param ids
     * @return list job
     */
    List<Job> getByIds(List<Long> ids);

    /**
     * Получение списка доступных услуг.
     *
     * @return list job
     */
    List<Job> getAvailable();

    /**
     * Получение списка услуг заявки по requestId.
     *
     * @param requestId
     * @return list job
     */
    List<Job> getLinksByRequestId(long requestId);
}
