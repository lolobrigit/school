package ru.edu.project.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

    /**
     * Настройка passwordEncoder.
     * Применяем готовый набор энкодеров.
     *
     * @return bean
     */
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
