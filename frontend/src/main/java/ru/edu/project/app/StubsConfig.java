package ru.edu.project.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.api.requests.RequestService;
import ru.edu.project.backend.stub.jobs.InMemoryStubJobService;
import ru.edu.project.backend.stub.requests.InMemoryStubRequestService;

@Configuration
@Profile("STUBS")
public class StubsConfig {

    /**
     * Заглушка сервиса.
     *
     * @return bean
     */
    @Bean
    public JobService jobServiceBean() {
        return new InMemoryStubJobService();
    }

    /**
     * Заглушка сервиса.
     * @param jobService
     * @return bean
     */
    @Bean
    public RequestService requestServiceBean(final JobService jobService) {
        return new InMemoryStubRequestService(jobService);
    }

}
