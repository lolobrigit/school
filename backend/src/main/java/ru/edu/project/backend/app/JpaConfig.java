package ru.edu.project.backend.app;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("SPRING_DATA")
@EnableJpaRepositories("ru.edu.project.backend.da.jpa.*")
@EntityScan("ru.edu.project.backend.da.jpa.*")
public class JpaConfig {
}
