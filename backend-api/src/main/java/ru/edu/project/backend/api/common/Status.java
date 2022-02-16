package ru.edu.project.backend.api.common;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = SimpleStatus.class)
public interface Status {

    /**
     * Код статуса.
     *
     * @return code
     */
    Long getCode();

    /**
     * Текст статуса.
     *
     * @return code
     */
    String getMessage();

}
