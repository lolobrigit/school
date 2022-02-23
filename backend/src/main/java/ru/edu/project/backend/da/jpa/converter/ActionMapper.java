package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.action.SimpleAction;
import ru.edu.project.backend.da.jpa.entity.ActionEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    /**
     * Маппинг List<Entity> -> List<Action>.
     *
     * @param list
     * @return list
     */
    List<Action> map(List<ActionEntity> list);

    /**
     * Маппинг Entity -> Action.
     *
     * @param entity
     * @return action
     */
    @Mapping(source = "pk.actionId", target = "typeCode")
    @Mapping(expression = "java(ru.edu.project.backend.model.ActionType.messageByCode(entity.getPk().getActionId()))", target = "typeMessage")
    @Mapping(source = "pk.time", target = "time")
    SimpleAction map(ActionEntity entity);

    /**
     * Маппинг Action -> Entity.
     *
     * @param action
     * @return entity
     */
    @Mapping(source = "typeCode", target = "pk.actionId")
    @Mapping(source = "time", target = "pk.time")
    ActionEntity map(Action action);
}
