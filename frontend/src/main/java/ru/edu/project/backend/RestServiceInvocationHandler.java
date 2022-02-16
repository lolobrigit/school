package ru.edu.project.backend;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.edu.project.backend.api.common.AcceptorArgument;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class RestServiceInvocationHandler implements InvocationHandler {

    /**
     * базовый адрес http://host:port для вызовов.
     */
    @Value("${apiService.baseUrl}")
    private String baseUrl;

    /**
     * Адрес сервиса.
     */
    @Setter
    private String serviceUrl;

    /**
     * Шаблон restTemplate.
     */
    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Вызов проксируемого метода.
     *
     * @param proxy
     * @param method
     * @param args
     * @return answer
     * @throws Throwable
     */
    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

        if (method.getDeclaredAnnotation(AcceptorArgument.class) != null) {
            return post(method, args);
        }

        return get(method, args);
    }

    private Object post(final Method method, final Object[] args) {
        StringBuilder sb = getUrl(method);
        Type genericReturnType = method.getGenericReturnType();
        return restTemplate.exchange(
                sb.toString(),
                HttpMethod.POST,
                null,
                ParameterizedTypeReference.forType(genericReturnType)
        ).getBody();
    }

    private Object get(final Method method, final Object[] args) {
        StringBuilder sb = getUrl(method);
        for (Object arg : args) {
            sb.append("/").append(arg.toString());
        }

        Type genericReturnType = method.getGenericReturnType();
        return restTemplate.exchange(
                sb.toString(),
                HttpMethod.GET,
                null,
                ParameterizedTypeReference.forType(genericReturnType)
        ).getBody();
    }

    private StringBuilder getUrl(final Method method) {
        return new StringBuilder(baseUrl)
                .append(serviceUrl)
                .append("/")
                .append(getServiceName(method));
    }

    private String getServiceName(final Method method) {
        return method.getName();
    }
}
