package ru.edu.project.backend.api.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@Jacksonized
public class RequestForm {

    /**
     * id клиента.
     */
    private Long clientId;

    /**
     * Желаемое время визита.
     */
    private Timestamp desiredTimeToVisit;

    /**
     * Выбранные услуги.
     */
    private List<Long> selectedJobs;

    /**
     * Комментарий.
     */
    private String comment;

}
