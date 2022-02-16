package ru.edu.project.backend.api.inventories;

import java.util.List;

public interface InventoryService {

    /**
     * Регистрация поступившего имущества с присвоением UUID.
     *
     * @param request
     * @return запись
     */
    InventoryInfo registerInventory(InventoryRequest request);

    /**
     * Получение списка имущества по заявке.
     * @param requestId
     * @return список
     */
    List<InventoryInfo> getInventoryByRequestId(long requestId);

    /**
     * Обновление статуса имущества.
     * @param request
     * @return запись
     */
    InventoryInfo updateStatus(InventoryStatusRequest request);
}
