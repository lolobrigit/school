package ru.edu.project.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


@Getter
@AllArgsConstructor
public enum ActionType {

    /**
     * Создание.
     */
    CREATED(1L, "Заявка создана"),

    /**
     * Изменение.
     */
    STATUS_CHANGED(2L, "Изменение статуса");

    /**
     * Код.
     */
    private Long typeCode;

    /**
     * Описание.
     */
    private String typeMessage;


    /**
     * Поиск типа по коду.
     *
     * @param code
     * @return enum or null
     */
    public static ActionType byCode(final long code) {
        return Arrays.stream(values()).filter(e -> e.getTypeCode() == code).findFirst().orElse(null);
    }

    /**
     * Описание типа по коду.
     *
     * @param code
     * @return str or null
     */
    public static String messageByCode(final long code) {
        ActionType type = byCode(code);
        if (type != null) {
            return type.getTypeMessage();
        }
        return null;
    }

}
