package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.action.ActionService;
import ru.edu.project.backend.api.action.CreateActionRequest;
import ru.edu.project.backend.service.ActionServiceLayer;

import java.util.List;

@RestController
@RequestMapping("/action")
public class ActionController implements ActionService {

    /**
     * Зависимость на делегата.
     */
    @Autowired
    private ActionServiceLayer delegate;

    /**
     * Поиск действий по заявке.
     *
     * @param requestId
     * @return list
     */
    @Override
    @GetMapping("/searchByRequest/{requestId}")
    public List<Action> searchByRequest(@PathVariable("requestId") final long requestId) {
        return delegate.searchByRequest(requestId);
    }

    /**
     * Создание действия у заявки.
     *
     * @param createActionRequest
     * @return list
     */
    @Override
    @PostMapping("/createAction")
    public Action createAction(@RequestBody final CreateActionRequest createActionRequest) {
        return delegate.createAction(createActionRequest);
    }
}
