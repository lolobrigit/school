package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.requests.RequestInfo;
import ru.edu.project.backend.model.RequestStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access слой для request.
 */
@Service
@Profile("JDBC_TEMPLATE")
public class RequestDA implements ru.edu.project.backend.da.RequestDALayer {

    /**
     * Запрос для поиска заявок клиента.
     */
    public static final String QUERY_FOR_CLIENT_ID = "SELECT * FROM request WHERE client_id = ?";

    /**
     * Запрос для поиска заявки по id.
     */
    public static final String QUERY_FOR_ID = "SELECT * FROM request WHERE id = ?";

    /**
     * Обновление заявки.
     * Обновляем только изменяемые поля.
     */
    public static final String QUERY_FOR_UPDATE = "UPDATE request SET status = :status, last_action_time = :last_action_time, closed_time = :closed_time, price = :price, comment = :comment WHERE id = :id";

    /**
     * Зависимость на шаблон jdbc.
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * Зависимость на шаблон jdbc insert.
     */
    private SimpleJdbcInsert jdbcInsert;

    /**
     * Зависимость на шаблон jdbc named.
     */
    @Autowired
    private NamedParameterJdbcTemplate jdbcNamed;

    /**
     * Внедрение зависимости jdbc insert с настройкой для таблицы.
     *
     * @param bean
     */
    @Autowired
    public void setJdbcInsert(final SimpleJdbcInsert bean) {
        jdbcInsert = bean
                .withTableName("request")
                .usingGeneratedKeyColumns("id");

    }

    /**
     * Поиск заявок клиента.
     *
     * @param clientId
     * @return list request
     */
    @Override
    public List<RequestInfo> getClientRequests(final long clientId) {
        return jdbcTemplate.query(QUERY_FOR_CLIENT_ID, this::rowMapper, clientId);
    }

    /**
     * Поиск заявки клиента.
     *
     * @param id
     * @return request
     */
    @Override
    public RequestInfo getById(final long id) {
        return jdbcTemplate.query(QUERY_FOR_ID, this::singleRowMapper, id);
    }

    /**
     * Сохранение заявки.
     *
     * @param draft
     * @return
     */
    @Override
    public RequestInfo save(final RequestInfo draft) {
        if (draft.getId() == null) {
            return insert(draft);
        }
        return update(draft);
    }

    private RequestInfo update(final RequestInfo draft) {
        jdbcNamed.update(QUERY_FOR_UPDATE, toMap(draft));
        return draft;
    }


    private RequestInfo insert(final RequestInfo draft) {
        long id = jdbcInsert.executeAndReturnKey(toMap(draft)).longValue();
        draft.setId(id);
        return draft;
    }

    private Map<String, Object> toMap(final RequestInfo draft) {
        HashMap<String, Object> map = new HashMap<>();
        if (draft.getId() != null) {
            map.put("id", draft.getId());
        }

        map.put("client_id", draft.getClientId());
        map.put("status", draft.getStatus().getCode());
        map.put("created_time", draft.getCreatedAt());
        map.put("planned_visit_time", draft.getPlannedVisitAt());
        map.put("last_action_time", draft.getLastActionAt());
        map.put("closed_time", draft.getClosedAt());
        map.put("price", draft.getPrice());
        map.put("comment", draft.getComment());

        return map;
    }


    @SneakyThrows
    private RequestInfo rowMapper(final ResultSet rs, final int pos) {
        return mapRow(rs);
    }

    @SneakyThrows
    private RequestInfo singleRowMapper(final ResultSet rs) {
        rs.next();
        return mapRow(rs);
    }

    private RequestInfo mapRow(final ResultSet rs) throws SQLException {
        return RequestInfo.builder()
                .id(rs.getLong("id"))
                .clientId(rs.getLong("client_id"))
                .status(RequestStatus.byCode(rs.getLong("status")))
                .createdAt(rs.getTimestamp("created_time"))
                .plannedVisitAt(rs.getTimestamp("planned_visit_time"))
                .lastActionAt(rs.getTimestamp("last_action_time"))
                .closedAt(rs.getTimestamp("closed_time"))
                .price(rs.getBigDecimal("price"))
                .comment(rs.getString("comment"))
                .build();
    }
}
