package ru.edu.project.backend.stub.jobs;

import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.api.jobs.JobService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class InMemoryStubJobService implements JobService {

    /**
     * @inheritDoc
     */
    @Override
    public List<Job> getAvailable() {
        return Arrays.stream(JobsEnum.values())
                .map(JobsEnum::getJob)
                .collect(Collectors.toList());
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<Job> getByIds(final List<Long> ids) {
        return Arrays.stream(JobsEnum.values())
                .filter(e -> ids.contains(e.getJob().getId()))
                .map(JobsEnum::getJob)
                .collect(Collectors.toList());
    }


    public enum JobsEnum {

        /**
         * Отладочная услуга.
         */
        JOB_1(1L, "Услуга 1"),

        /**
         * Отладочная услуга.
         */
        JOB_2(2L, "Услуга 2"),

        /**
         * Отладочная услуга.
         */
        JOB_3(3L, "Услуга 3"),

        /**
         * Отладочная услуга.
         */
        JOB_4(4L, "Услуга 4");


        /**
         * Связанный объект.
         */
        private Job job;

        JobsEnum(final Long code, final String title) {
            job = Job.builder()
                    .id(code)
                    .title(title)
                    .build();
        }

        /**
         * Получение объекта.
         *
         * @return job
         */
        public Job getJob() {
            return job;
        }
    }

}
