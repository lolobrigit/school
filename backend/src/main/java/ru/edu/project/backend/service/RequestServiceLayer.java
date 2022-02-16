package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.requests.RequestForm;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.api.requests.RequestService;
import ru.edu.project.backend.da.RequestDALayer;
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
        RequestInfo requestInfo = daLayer.getById(requestId);

        if (requestInfo == null || requestInfo.getClientId() != clientId) {
            throw new IllegalArgumentException("request for client not found");
        }

        requestInfo.setServices(jobService.getByLink(requestId));

        //подгрузка истории

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

        RequestInfo draft = RequestInfo.builder()
                .clientId(requestForm.getClientId())
                .createdAt(new Timestamp(new Date().getTime()))
                .plannedVisitAt(requestForm.getDesiredTimeToVisit())
                .comment(requestForm.getComment())
                .status(RequestStatus.CREATED)
                .build();

        daLayer.save(draft);

        jobService.link(draft.getId(), requestForm.getSelectedJobs());

        draft.setServices(jobService.getByIds(requestForm.getSelectedJobs()));

        //создать действие "Создана заявка"

        return draft;
    }
}
