package ru.edu.project.app;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Инициализатор сервлета спринга.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Настраиваем приложение.
     * Указываем какой класс с конфигурацией используется для настройки
     *
     * @param application
     * @return
     */
    @Override
    protected SpringApplicationBuilder configure(
            final SpringApplicationBuilder application
    ) {
        return application.sources(DemoFrontendApplication.class);
    }

}
