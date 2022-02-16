package ru.edu.project.backend.da;

import ru.edu.project.backend.api.requests.RequestInfo;

import java.util.List;

public interface RequestDALayer {

    /**
     * Получение списка заявок по clientId.
     *
     * @param clientId
     * @return list request
     */
    List<RequestInfo> getClientRequests(long clientId);

    /**
     * Получение заявки по id.
     *
     * @param id
     * @return request
     */
    RequestInfo getById(long id);

    /**
     * Сохранение (создание/обновление) заявки.
     *
     * @param draft
     * @return request
     */
    RequestInfo save(RequestInfo draft);
}
