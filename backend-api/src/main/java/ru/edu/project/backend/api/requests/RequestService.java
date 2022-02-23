package ru.edu.project.backend.api.requests;


import ru.edu.project.backend.api.common.AcceptorArgument;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;

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
     * Получение детальной информации по заявке с проверкой принадлежности клиенту.
     *
     * @param clientId
     * @param requestId
     * @return запись
     */
    RequestInfo getDetailedInfo(long clientId, long requestId);

    /**
     * Получение детальной информации по заявке.
     *
     * @param requestId
     * @return запись
     */
    RequestInfo getDetailedInfo(long requestId);

    /**
     * Регистрация новой заявки.
     *
     * @param requestForm
     * @return запись
     */
    @AcceptorArgument
    RequestInfo createRequest(RequestForm requestForm);

    /**
     * Метод для поиска заявок.
     *
     * @param recordSearch
     * @return list
     */
    @AcceptorArgument
    PagedView<RequestInfo> searchRequests(RecordSearch recordSearch);


    /**
     * Изменение статуса заявки.
     *
     * @param updateStatusRequest
     * @return boolean
     */
    @AcceptorArgument
    boolean updateStatus(UpdateStatusRequest updateStatusRequest);

}
