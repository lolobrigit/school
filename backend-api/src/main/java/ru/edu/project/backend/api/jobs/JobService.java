package ru.edu.project.backend.api.jobs;

import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface JobService {

    /**
     * Получение доступных услуг.
     *
     * @return список
     */
    List<Job> getAvailable();

    /**
     * Получение услуг по коду.
     *
     * @param ids
     * @return список
     */
    @AcceptorArgument
    List<Job> getByIds(List<Long> ids);

}
