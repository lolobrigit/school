package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.common.PagedView;
import ru.edu.project.backend.api.common.RecordSearch;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.da.RequestDALayer;
import ru.edu.project.backend.da.jpa.converter.RequestInfoMapper;
import ru.edu.project.backend.da.jpa.entity.RequestEntity;
import ru.edu.project.backend.da.jpa.repository.RequestEntityRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    /**
     * Поиск заявок.
     *
     * @param recordSearch
     * @return list
     */
    @Override
    public PagedView<RequestInfo> search(final RecordSearch recordSearch) {
        Sort.Direction direction = recordSearch.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(recordSearch.getPage(), recordSearch.getPerPage(), Sort.by(direction, recordSearch.getOrderBy()));

        Page<RequestEntity> page = repo.findAll(pageRequest);

        return PagedView.<RequestInfo>builder()
                .elements(mapper.mapList(page.get().collect(Collectors.toList())))
                .page(recordSearch.getPage())
                .perPage(recordSearch.getPerPage())
                .total(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
