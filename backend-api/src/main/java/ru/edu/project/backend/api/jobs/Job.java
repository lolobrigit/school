package ru.edu.project.backend.api.jobs;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class Job implements JobAbstract {

    /**
     * Код услуги.
     */
    private Long id;

    /**
     * Название услуги.
     */
    private String title;

    /**
     * Описание услуги.
     */
    private String desc;
}
