package ru.edu.project.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.edu.project.backend.api.common.Status;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RequestStatus implements Status {

    /**
     * Заявка создана.
     */
    CREATED(1L, "Создан"),

    /**
     * Заявка в работе.
     */
    IN_PROGRESS(2L, "В работе"),

    /**
     * Заявка ожидает поставку комплектующих.
     */
    WAIT_SUPPLY(3L, "Ожидает поставку комплектующих"),

    /**
     * Заявка готова к выдаче.
     */
    READY(3L, "Готов к выдаче"),

    /**
     * Заявка закрыта.
     */
    COMPLETED(4L, "Завершен");

    /**
     * Код.
     */
    private final Long code;

    /**
     * Сообщение.
     */
    private final String message;

    /**
     * Получение enum статуса по его коду.
     *
     * @param status
     * @return enum or null
     */
    public static Status byCode(final long status) {
        return Arrays.stream(values()).filter(s -> s.getCode() == status).findFirst().orElse(null);
    }
}
