package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.da.JobDALayer;

import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("JobServiceLayer")
public class JobServiceLayer implements JobService {

    /**
     * Зависимость для слоя доступа к данным услуг.
     */
    @Autowired
    private JobDALayer daLayer;

    /**
     * Получение доступных услуг.
     *
     * @return список
     */
    @Override
    public List<Job> getAvailable() {
        return daLayer.getAvailable();
    }

    /**
     * Получение услуг по коду.
     *
     * @param ids
     * @return список
     */
    @Override
    public List<Job> getByIds(final List<Long> ids) {
        return daLayer.getByIds(ids);
    }

    /**
     * Получение услуг связанных с заявкой по requestId.
     *
     * @param requestId
     * @return list job
     */
    public List<Job> getByLink(final long requestId) {
        return daLayer.getLinksByRequestId(requestId);
    }

    /**
     * Связывание заявки и услуги.
     *
     * @param requestId
     * @param ids
     */
    public void link(final long requestId, final List<Long> ids) {
        ids.forEach(id -> daLayer.linkRequest(requestId, id));
    }
}
