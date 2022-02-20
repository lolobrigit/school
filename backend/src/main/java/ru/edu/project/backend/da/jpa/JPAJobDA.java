package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.da.JobDALayer;
import ru.edu.project.backend.da.jpa.converter.JobMapper;
import ru.edu.project.backend.da.jpa.entity.JobEntity;
import ru.edu.project.backend.da.jpa.entity.JobLinkEntity;
import ru.edu.project.backend.da.jpa.repository.JobEntityRepository;
import ru.edu.project.backend.da.jpa.repository.JobLinkEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Profile("SPRING_DATA")
public class JPAJobDA implements JobDALayer {

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private JobEntityRepository jobRepo;

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private JobLinkEntityRepository linkRepo;

    /**
     * Зависимость на маппер.
     */
    @Autowired
    private JobMapper jobMapper;

    /**
     * Связывание запроса и услуги.
     *
     * @param requestId
     * @param jobId
     */
    @Override
    public void linkRequest(final long requestId, final long jobId) {
        JobLinkEntity entityDraft = new JobLinkEntity();
        entityDraft.setPk(JobLinkEntity.pk(requestId, jobId));
        entityDraft.setJob(getJobEntityById(jobId));

        linkRepo.save(entityDraft);
    }

    private JobEntity getJobEntityById(final Long id) {
        Optional<JobEntity> jobEntity = jobRepo.findById(id);
        if (!jobEntity.isPresent()) {
            throw new RuntimeException("Job not found by jobId " + id);
        }
        return jobEntity.get();
    }


    /**
     * Получение услуги по id.
     *
     * @param id
     * @return job
     */
    @Override
    public Job getById(final Long id) {
        return jobMapper.map(getJobEntityById(id));
    }

    /**
     * Получение списка услуг по ids.
     *
     * @param ids
     * @return list job
     */
    @Override
    public List<Job> getByIds(final List<Long> ids) {
        return jobMapper.map(jobRepo.findAllById(ids));
    }

    /**
     * Получение списка доступных услуг.
     *
     * @return list job
     */
    @Override
    public List<Job> getAvailable() {
        return jobMapper.map(jobRepo.findAllByEnabled(true));
    }

    /**
     * Получение списка услуг заявки по requestId.
     *
     * @param requestId
     * @return list job
     */
    @Override
    public List<Job> getLinksByRequestId(final long requestId) {
        List<JobLinkEntity> jobsEntities = linkRepo.findAllByPkRequestId(requestId);
        return jobMapper.map(jobsEntities.stream().map(JobLinkEntity::getJob).collect(Collectors.toList()));
    }
}
