package ru.edu.project.backend.api.action;

import ru.edu.project.backend.api.common.AcceptorArgument;

import java.util.List;

public interface ActionService {

    /**
     * Поиск действий по заявке.
     *
     * @param requestId
     * @return list
     */
    List<Action> searchByRequest(long requestId);

    /**
     * Создание действия у заявки.
     *
     * @param createActionRequest
     * @return list
     */
    @AcceptorArgument
    Action createAction(CreateActionRequest createActionRequest);

}
