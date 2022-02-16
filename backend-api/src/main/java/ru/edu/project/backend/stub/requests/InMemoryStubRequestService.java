package ru.edu.project.backend.stub.requests;

import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.StatusImpl;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.api.requests.RequestForm;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.api.requests.RequestService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Arrays.asList;


public class InMemoryStubRequestService implements RequestService {

    /**
     * Ссылка на сервис услуг.
     */
    private JobService servicesService;

    /**
     * Локальное хранилище заявок в RAM.
     */
    private Map<Long, List<RequestInfo>> db = new ConcurrentHashMap<>();

    /**
     * Локальный счетчик заявок.
     */
    private AtomicLong idCount = new AtomicLong(0);

    /**
     * Конструктор с зависимостью.
     *
     * @param bean
     */
    public InMemoryStubRequestService(final JobService bean) {
        servicesService = bean;
    }

    /**
     * @inheritDoc
     */
    @Override
    public List<RequestInfo> getRequestByClient(final long id) {
        if (db.containsKey(id)) {
            return db.get(id);
        }
        return Collections.emptyList();
    }

    /**
     * @inheritDoc
     */
    @Override
    public RequestInfo getDetailedInfo(
            final long clientId,
            final long requestId
    ) {
        if (db.containsKey(clientId)) {
            Optional<RequestInfo> info = db.get(clientId).stream()
                    .filter(requestInfo -> requestId == requestInfo.getId())
                    .findFirst();
            return info.orElse(null);
        }
        return null;
    }

    /**
     * @inheritDoc
     */
    @Override
    public RequestInfo createRequest(final RequestForm requestForm) {

        RequestInfo info = RequestInfo.builder()
                .id(idCount.addAndGet(1))
                .clientId(requestForm.getClientId())
                .createdAt(new Timestamp(new Date().getTime()))
                .plannedVisitAt(requestForm.getDesiredTimeToVisit())
                .status(StatusImpl.builder()
                        .code(1L)
                        .message("Создана")
                        .build())
                .comment(requestForm.getComment())
                .services(getJobsById(requestForm))
                .actionHistory(asList(Action.builder()
                        .time(new Timestamp(new Date().getTime()))
                        .typeCode(1L)
                        .typeMessage("Создание")
                        .message("Заявка создана")
                        .build()))
                .build();

        if (!db.containsKey(requestForm.getClientId())) {
            db.put(requestForm.getClientId(), new ArrayList<>());
        }

        db.get(requestForm.getClientId()).add(info);

        return info;
    }

    private List<Job> getJobsById(final RequestForm requestForm) {
        return servicesService.getByIds(requestForm.getSelectedJobs());
    }

}
