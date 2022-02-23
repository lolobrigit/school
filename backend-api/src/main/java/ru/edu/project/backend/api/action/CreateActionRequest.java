package ru.edu.project.backend.api.action;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CreateActionRequest {

    /**
     * Id заявки.
     */
    private long requestId;

    /**
     * Действие.
     */
    private Action action;

}
