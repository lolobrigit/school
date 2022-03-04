package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.edu.project.backend.api.requests.RequestInfo;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static ru.edu.project.backend.da.jdbctemplate.RequestDA.QUERY_FOR_CLIENT_ID;

public class RequestDATest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    private RequestDA da;

    @Before
    public void setUp() throws Exception {
        openMocks(this);
    }

    @Test
    @SneakyThrows
    public void getClientRequests() {

        ResultSet resultSetMock = mock(ResultSet.class);
        Long clientId = 123L;
        List<RequestInfo> expectedResult = new ArrayList();

        Long reqId = 1234L;
        when(resultSetMock.getLong("id")).thenReturn(reqId);
        when(resultSetMock.getLong("client_id")).thenReturn(clientId);
        when(resultSetMock.getLong("status")).thenReturn(1L);
        Timestamp created_ts = new Timestamp(1L);
        when(resultSetMock.getTimestamp("created_time")).thenReturn(created_ts);

        Timestamp planned_ts = new Timestamp(2L);
        when(resultSetMock.getTimestamp("planned_visit_time")).thenReturn(planned_ts);

        Timestamp last_ts = new Timestamp(3L);
        when(resultSetMock.getTimestamp("last_action_time")).thenReturn(last_ts);

        Timestamp closed_ts = new Timestamp(4L);
        when(resultSetMock.getTimestamp("closed_time")).thenReturn(closed_ts);

        when(resultSetMock.getBigDecimal("price")).thenReturn(BigDecimal.TEN);
        when(resultSetMock.getString("comment")).thenReturn("commentMock");

        when(jdbcTemplate.query(eq(QUERY_FOR_CLIENT_ID), any(RowMapper.class), eq(clientId))).thenAnswer(invocation -> {
            RowMapper<RequestInfo> rowMapper = invocation.getArgument(1, RowMapper.class);
            expectedResult.add(rowMapper.mapRow(resultSetMock, 1));
            return expectedResult;
        });


        List<RequestInfo> list = da.getClientRequests(clientId);

        verify(jdbcTemplate).query(eq(QUERY_FOR_CLIENT_ID), any(RowMapper.class), eq(clientId));

        assertEquals(1, list.size());

        RequestInfo info = list.get(0);
        assertEquals(reqId, info.getId());
        assertEquals(clientId, info.getClientId());

        assertEquals(created_ts, info.getCreatedAt());
        //...


    }
}