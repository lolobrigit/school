package ru.edu.project.backend.api.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.inventories.InventoryInfo;
import ru.edu.project.backend.api.jobs.Job;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class RequestInfo {

    /**
     * id заявки.
     */
    private Long id;

    /**
     * id клиента.
     */
    private Long clientId;

    /**
     * Статус.
     */
    private Status status;

    /**
     * Время создания заявки.
     */
    private Timestamp createdAt;

    /**
     * Запланированное время визита.
     */
    private Timestamp plannedVisitAt;

    /**
     * Время последнего изменения заявки.
     */
    private Timestamp lastActionAt;

    /**
     * Время закрытия заявки.
     */
    private Timestamp closedAt;

    /**
     * Стоимость заявки.
     */
    private BigDecimal price;

    /**
     * Комментарий клиента.
     */
    private String comment;

    /**
     * Выбранные услуги.
     */
    private List<Job> services;

    /**
     * История действий.
     */
    private List<Action> actionHistory;

    /**
     * Список имущества.
     */
    private List<InventoryInfo> inventoryList;

}
