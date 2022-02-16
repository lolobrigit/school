package ru.edu.project.backend.api.inventories;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Builder
@Getter
@Jacksonized
public class InventoryRequest {

    /**
     * id заявки.
     */
    private Long requestId;

    /**
     * Код имущества.
     */
    private Long inventoryType;

    /**
     * Описание имущества.
     */
    private String message;

}
