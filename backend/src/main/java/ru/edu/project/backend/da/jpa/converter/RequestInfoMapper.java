package ru.edu.project.backend.da.jpa.converter;

import org.mapstruct.Mapper;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.da.jpa.entity.RequestEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestInfoMapper {

    /**
     * Маппер RequestInfo -> RequestEntity.
     *
     * @param requestInfo
     * @return entity
     */
    RequestEntity map(RequestInfo requestInfo);

    /**
     * Маппер RequestEntity -> RequestInfo.
     *
     * @param entity
     * @return requestInfo
     */
    RequestInfo map(RequestEntity entity);

    /**
     * Маппер List<RequestEntity> -> List<RequestInfo>.
     *
     * @param listEntity
     * @return list requestInfo
     */
    List<RequestInfo> mapList(List<RequestEntity> listEntity);
}
