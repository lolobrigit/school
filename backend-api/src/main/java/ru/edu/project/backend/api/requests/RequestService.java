package ru.edu.project.backend.api.requests;


import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface RequestService {

    /**
     * Получение заказов клиента.
     *
     * @param id
     * @return список
     */
    List<RequestInfo> getRequestByClient(long id);

    /**
     * Получение детальной информации по заявке.
     * @param clientId
     * @param requestId
     * @return запись
     */
    RequestInfo getDetailedInfo(long clientId, long requestId);

    /**
     * Регистрация новой заявки.
     *
     * @param requestForm
     * @return запись
     */
    @AcceptorArgument
    RequestInfo createRequest(RequestForm requestForm);
}
