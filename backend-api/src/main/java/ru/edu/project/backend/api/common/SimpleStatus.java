package ru.edu.project.backend.api.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleStatus implements Status {

    /**
     * Код статуса.
     */
    private Long code;

    /**
     * Строковый статус.
     */
    private String message;


    /**
     * Пустой конструктор.
     */
    public SimpleStatus() {
    }

    /**
     * Конструктор.
     *
     * @param str
     */
    public SimpleStatus(final String str) {
        message = str;
    }


}
