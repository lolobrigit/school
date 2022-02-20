package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.da.RequestDALayer;
import ru.edu.project.backend.da.jpa.converter.RequestInfoMapper;
import ru.edu.project.backend.da.jpa.entity.RequestEntity;
import ru.edu.project.backend.da.jpa.repository.RequestEntityRepository;

import java.util.List;
import java.util.Optional;

@Service
@Profile("SPRING_DATA")
public class JPARequestDA implements RequestDALayer {

    /**
     * Зависимость на репозиторий.
     */
    @Autowired
    private RequestEntityRepository repo;

    /**
     * Зависимость на маппер.
     */
    @Autowired
    private RequestInfoMapper mapper;

    /**
     * Получение списка заявок по clientId.
     *
     * @param clientId
     * @return list request
     */
    @Override
    public List<RequestInfo> getClientRequests(final long clientId) {
        return mapper.mapList(repo.findByClientId(clientId));
    }


    /**
     * Получение заявки по id.
     *
     * @param id
     * @return request
     */
    @Override
    public RequestInfo getById(final long id) {
        Optional<RequestEntity> entity = repo.findById(id);
        return entity.map(requestEntity -> mapper.map(requestEntity)).orElse(null);
    }

    /**
     * Сохранение (создание/обновление) заявки.
     *
     * @param draft
     * @return request
     */
    @Override
    public RequestInfo save(final RequestInfo draft) {
        RequestEntity entity = mapper.map(draft);

        RequestEntity saved = repo.save(entity);
        draft.setId(saved.getId());
        return mapper.map(saved);
    }
}
