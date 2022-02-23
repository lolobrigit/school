package ru.edu.project.backend.api.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.common.Status;

@Getter
@Builder
@Jacksonized
public class UpdateStatusRequest {

    /**
     * Идентификатор заявки.
     */
    private long requestId;

    /**
     * Статус в который нужно перевести заявку.
     */
    private Status status;
}
