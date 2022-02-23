package ru.edu.project.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.action.ActionService;
import ru.edu.project.backend.api.action.CreateActionRequest;
import ru.edu.project.backend.api.action.SimpleAction;
import ru.edu.project.backend.da.ActionDALayer;
import ru.edu.project.backend.model.ActionType;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class ActionServiceLayer implements ActionService {

    /**
     * Зависимость на слой доступа к данным.
     */
    @Autowired
    private ActionDALayer actionDALayer;

    /**
     * @inheritDoc
     */
    @Override
    public List<Action> searchByRequest(final long requestId) {
        return actionDALayer.findByRequest(requestId);
    }

    /**
     * @inheritDoc
     */
    @Override
    public Action createAction(final CreateActionRequest createActionRequest) {
        ActionType actionType = ActionType.byCode(createActionRequest.getAction().getTypeCode());
        if (actionType == null) {
            throw new IllegalArgumentException("invalid action code " + createActionRequest.getAction().getTypeCode());
        }

        Timestamp timestamp = createActionRequest.getAction().getTime();
        if (timestamp == null) {
            timestamp = new Timestamp(new Date().getTime());
        }

        return actionDALayer.save(
                createActionRequest.getRequestId(),
                SimpleAction.builder()
                        .typeCode(actionType.getTypeCode())
                        .time(timestamp)
                        .message(createActionRequest.getAction().getMessage())
                        .build());
    }
}
