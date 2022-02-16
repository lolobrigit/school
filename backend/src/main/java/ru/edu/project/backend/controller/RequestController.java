package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.requests.RequestForm;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.api.requests.RequestService;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController implements RequestService {

    /**
     * Делегат для передачи вызова.
     */
    @Autowired
    private RequestService delegate;

    /**
     * Получение заказов клиента.
     *
     * @param id
     * @return список
     */
    @Override
    @GetMapping("/getRequestByClient/{id}")
    public List<RequestInfo> getRequestByClient(@PathVariable("id") final long id) {
        return delegate.getRequestByClient(id);
    }

    /**
     * Получение детальной информации по заявке.
     *
     * @param clientId
     * @param requestId
     * @return запись
     */
    @Override
    @GetMapping("/getDetailedInfo/{clientId}/{requestId}")
    public RequestInfo getDetailedInfo(
            @PathVariable("clientId") final long clientId,
            @PathVariable("requestId") final long requestId
    ) {
        return delegate.getDetailedInfo(clientId, requestId);
    }

    /**
     * Регистрация новой заявки.
     *
     * @param requestForm
     * @return запись
     */
    @Override
    @PostMapping("/createRequest")
    public RequestInfo createRequest(@RequestBody final RequestForm requestForm) {
        return delegate.createRequest(requestForm);
    }
}
