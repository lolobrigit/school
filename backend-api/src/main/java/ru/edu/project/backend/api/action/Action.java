package ru.edu.project.backend.api.action;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.sql.Timestamp;

@JsonDeserialize(as = SimpleAction.class)
public interface Action {

    /**
     * Код действия.
     *
     * @return long
     */
    Long getTypeCode();

    /**
     * Описание действия.
     *
     * @return string
     */
    String getTypeMessage();

    /**
     * Время действия.
     *
     * @return timestamp
     */
    Timestamp getTime();

    /**
     * Сообщение действия.
     *
     * @return string
     */
    String getMessage();
}
