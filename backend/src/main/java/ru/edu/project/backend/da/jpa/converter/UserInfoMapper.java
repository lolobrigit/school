package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.da.jpa.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    /**
     * Маппинг Entity -> Object.
     *
     * @param entity
     * @return obj
     */
    UserInfo map(UserEntity entity);

    /**
     * Маппинг Obj -> Entity.
     *
     * @param obj
     * @return entity
     */
    UserEntity map(UserInfo obj);
}
