package ru.edu.project.backend.app;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.api.requests.RequestService;
import ru.edu.project.backend.stub.jobs.InMemoryStubJobService;
import ru.edu.project.backend.stub.requests.InMemoryStubRequestService;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@SpringBootApplication
@ComponentScan({"ru.edu.project.backend"})
public class DemoBackendApplication {

    /**
     * Точка входа.
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(DemoBackendApplication.class, args);
    }


    /**
     * Настройкаи заглушки бина jobService.
     *
     * @return stubJobService
     */
    @Bean
    @Profile("STUB")
    @Qualifier("JobServiceLayer")
    public JobService stubJobService() {
        return new InMemoryStubJobService();
    }

    /**
     * Настройкаи заглушки бина requestService.
     *
     * @param jobService
     * @return stubRequestService
     */
    @Bean
    @Profile("STUB")
    @Qualifier("RequestServiceLayer")
    public RequestService stubRequestService(final JobService jobService) {
        return new InMemoryStubRequestService(jobService);
    }

    /**
     * Настройка бина для шаблона jdbc insert.
     *
     * @param template
     * @return bean
     */
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    @Profile("JDBC_TEMPLATE")
    public SimpleJdbcInsert jdbcInsertBean(final JdbcTemplate template) {
        return new SimpleJdbcInsert(template);
    }


    /**
     * Настройка бина для шаблона jdbc named.
     *
     * @param template
     * @return bean
     */
    @Bean
    @Scope(SCOPE_PROTOTYPE)
    @Profile("JDBC_TEMPLATE")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(final JdbcTemplate template) {
        return new NamedParameterJdbcTemplate(template);
    }
}
