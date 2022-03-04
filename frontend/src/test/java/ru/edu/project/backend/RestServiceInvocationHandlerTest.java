package ru.edu.project.backend;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import ru.edu.project.backend.api.common.AcceptorArgument;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class RestServiceInvocationHandlerTest {

    public static final String BASE_URL_MOCK = "/baseUrlMock";
    public static final String SERVICE_URL_MOCK = "/serviceUrlMock";
    public static final String BASE_URL_STR = BASE_URL_MOCK + SERVICE_URL_MOCK;
    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<String> stringResponseEntity;

    @Mock
    private ResponseEntity<List<String>> listResponseEntity;

    @InjectMocks
    private RestServiceInvocationHandler invocationHandler;


    @Before
    public void setUp() throws Exception {
        openMocks(this);
        ReflectionTestUtils.setField(invocationHandler, "baseUrl", BASE_URL_MOCK);
        invocationHandler.setServiceUrl(SERVICE_URL_MOCK);
    }

    @Test
    @SneakyThrows
    public void invoke() {
        Method getMethod = TestInterface.class.getMethod("getMethod", int.class, boolean.class, String.class);

        String expectedResult = "isOk";
        String expectedUrl = BASE_URL_STR + "/" + getMethod.getName() + "/88/true/mock";


        when(restTemplate.exchange(
                eq(expectedUrl),
                eq(HttpMethod.GET),
                isNull(),
                eq(ParameterizedTypeReference.forType(getMethod.getGenericReturnType()))
        )).thenAnswer(invocation -> stringResponseEntity);

        when(stringResponseEntity.getBody()).thenReturn(expectedResult);

        Object result = invocationHandler.invoke(this, getMethod, new Object[]{88, true, "mock"});
        assertEquals(expectedResult, result);

    }

    @Test
    @SneakyThrows
    public void invokePost() {
        Method postMethod = TestInterface.class.getMethod("postMethod", Map.class);
        String expectedUrl = BASE_URL_STR + "/" + postMethod.getName();

        Map<String, String> mapMock = new HashMap<>();

        when(restTemplate.exchange(
                any(RequestEntity.class),
                eq(ParameterizedTypeReference.forType(postMethod.getGenericReturnType()))
        )).thenAnswer(invocation -> {

            RequestEntity<Object> request = invocation.getArgument(0, RequestEntity.class);

            assertEquals(HttpMethod.POST, request.getMethod());
            assertEquals(new URI(expectedUrl), request.getUrl());
            assertEquals(mapMock, request.getBody());

            return listResponseEntity;
        });

        ArrayList<String> expectedResult = new ArrayList<>();
        when(listResponseEntity.getBody()).thenReturn(expectedResult);

        Object result = invocationHandler.invoke(this, postMethod, new Object[]{mapMock});
        assertEquals(expectedResult, result);

        verify(restTemplate).exchange(
                any(RequestEntity.class),
                eq(ParameterizedTypeReference.forType(postMethod.getGenericReturnType()))
        );

    }

    public interface TestInterface {

        String getMethod(int a, boolean b, String c);


        @AcceptorArgument
        List<String> postMethod(Map<String, String> map);

    }

}