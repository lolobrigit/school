package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.da.jpa.entity.JobEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface JobMapper {

    /**
     * Маппер JobEntity -> Job.
     *
     * @param entity
     * @return job
     */
    Job map(JobEntity entity);

    /**
     * Маппер List<JobEntity> -> List<Job>.
     * @param ids
     * @return list job
     */
    List<Job> map(Iterable<JobEntity> ids);
}
