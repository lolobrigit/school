package ru.edu.project.backend.api.common;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class StatusImpl implements Status {

    /**
     * Код статуса.
     */
    private Long code;

    /**
     * Текст статуса.
     */
    private String message;
}
