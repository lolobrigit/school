package ru.edu.project.backend.api.inventories;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ru.edu.project.backend.api.common.Status;

import java.util.UUID;

@Builder
@Getter
@Jacksonized
public class InventoryStatusRequest {

    /**
     * UUID имущества.
     */
    private UUID uuid;

    /**
     * Новый статус имущества.
     */
    private Status status;

}
