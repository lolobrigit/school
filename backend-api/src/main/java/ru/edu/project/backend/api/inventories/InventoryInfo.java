package ru.edu.project.backend.api.inventories;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.common.StatusImpl;

import java.util.UUID;

@Getter
@Builder
@Jacksonized
public class InventoryInfo {

    /**
     * UUID имущества.
     */
    private UUID uuid;

    /**
     * Код имущества.
     */
    private String type;

    /**
     * Описание имущества.
     */
    private String message;

    /**
     * Статус.
     */
    private StatusImpl status;

}
