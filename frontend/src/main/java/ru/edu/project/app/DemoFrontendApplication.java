package ru.edu.project.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.servlet.Filter;

@SpringBootApplication
@ComponentScan({"ru.edu.project"})
public class DemoFrontendApplication {

    /**
     * Точка входа.
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(DemoFrontendApplication.class, args);
    }

    /**
     * Создание фильтра для _method поля.
     *
     * @return bean
     */
    @Bean
    public Filter httpMethodFilterBean() {
        return new HiddenHttpMethodFilter();
    }
}
