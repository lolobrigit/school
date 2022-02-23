package ru.edu.project.backend.da;

import ru.edu.project.backend.api.action.Action;

import java.util.List;

public interface ActionDALayer {
    /**
     * Поиск действий по заявке.
     *
     * @param requestId
     * @return list
     */
    List<Action> findByRequest(long requestId);

    /**
     * Создание действия по заявке.
     *
     * @param requestId
     * @param build
     * @return action
     */
    Action save(long requestId, Action build);
}
