package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.action.CreateActionRequest;
import ru.edu.project.backend.api.action.SimpleAction;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.requests.RequestForm;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.api.requests.RequestService;
import ru.edu.project.backend.api.requests.UpdateStatusRequest;
import ru.edu.project.backend.da.RequestDALayer;
import ru.edu.project.backend.model.ActionType;
import ru.edu.project.backend.model.RequestStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Profile("!STUB")
@Qualifier("RequestServiceLayer")
public class RequestServiceLayer implements RequestService {

    /**
     * Зависимость для слоя доступа к данным заявок.
     */
    @Autowired
    private RequestDALayer daLayer;

    /**
     * Зависимость для сервиса услуг.
     */
    @Autowired
    private JobServiceLayer jobService;

    /**
     * Зависимость для сервиса действий.
     */
    @Autowired
    private ActionServiceLayer actionServiceLayer;

    /**
     * Получение заказов клиента.
     *
     * @param id
     * @return список
     */
    @Override
    public List<RequestInfo> getRequestByClient(final long id) {
        return daLayer.getClientRequests(id);
    }

    /**
     * Получение детальной информации по заявке.
     *
     * @param clientId
     * @param requestId
     * @return запись
     */
    @Override
    public RequestInfo getDetailedInfo(final long clientId, final long requestId) {
        RequestInfo requestInfo = getDetailedInfo(requestId);
        if (requestInfo.getClientId() != clientId) {
            throw new IllegalArgumentException("request for client not found");
        }

        return requestInfo;
    }

    /**
     * Получение детальной информации по заявке.
     *
     * @param requestId
     * @return запись
     */
    @Override
    public RequestInfo getDetailedInfo(final long requestId) {
        RequestInfo requestInfo = daLayer.getById(requestId);
        if (requestInfo == null) {
            throw new IllegalArgumentException("request for client not found");
        }
        requestInfo.setServices(jobService.getByLink(requestId));
        requestInfo.setActionHistory(actionServiceLayer.searchByRequest(requestId));

        return requestInfo;
    }

    /**
     * Регистрация новой заявки.
     *
     * @param requestForm
     * @return запись
     */
    @Override
    public RequestInfo createRequest(final RequestForm requestForm) {

        Timestamp createdAt = new Timestamp(new Date().getTime());
        RequestInfo draft = RequestInfo.builder()
                .clientId(requestForm.getClientId())
                .createdAt(createdAt)
                .plannedVisitAt(requestForm.getDesiredTimeToVisit())
                .comment(requestForm.getComment())
                .lastActionAt(createdAt)
                .status(RequestStatus.CREATED)
                .build();

        daLayer.save(draft);

        jobService.link(draft.getId(), requestForm.getSelectedJobs());

        draft.setServices(jobService.getByIds(requestForm.getSelectedJobs()));

        actionServiceLayer.createAction(CreateActionRequest.builder()
                .requestId(draft.getId())
                .action(SimpleAction.builder()
                        .typeCode(ActionType.CREATED.getTypeCode())
                        .build())
                .build());

        draft.setActionHistory(actionServiceLayer.searchByRequest(draft.getId()));

        return draft;
    }


    /**
     * Метод для поиска заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<RequestInfo> searchRequests(final RecordSearch recordSearch) {

        return daLayer.search(recordSearch);
    }

    /**
     * Изменение статуса заявки.
     *
     * @param updateStatusRequest
     * @return boolean
     */
    @Override
    public boolean updateStatus(final UpdateStatusRequest updateStatusRequest) {
        RequestInfo req = daLayer.getById(updateStatusRequest.getRequestId());

        //Синхронизируем статусы
        Status status = RequestStatus.byCode(updateStatusRequest.getStatus().getCode());

        if (req == null || status == null) {
            return false;
        }

        /*
         * проверяем условия перехода при необходимости
         */

        Long oldCode = req.getStatus().getCode();

        req.setStatus(status);
        Timestamp timestamp = new Timestamp(new Date().getTime());
        req.setLastActionAt(timestamp);
        daLayer.save(req);

        actionServiceLayer.createAction(CreateActionRequest.builder()
                .requestId(req.getId())
                .action(SimpleAction.builder()
                        .typeCode(ActionType.STATUS_CHANGED.getTypeCode())
                        .time(timestamp)
                        .message(oldCode + " > " + status.getCode())
                        .build())
                .build());

        return true;
    }
}
