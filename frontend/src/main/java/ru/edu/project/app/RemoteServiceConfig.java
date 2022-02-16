package ru.edu.project.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.edu.project.backend.RestServiceInvocationHandler;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.api.requests.RequestService;

import java.lang.reflect.Proxy;

@Configuration
@Profile("REST")
@SuppressWarnings("unchecked")
public class RemoteServiceConfig {

    /**
     * Создаем rest-прокси для RequestService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public RequestService requestServiceRest(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/request");
        return getProxy(handler, RequestService.class);
    }

    /**
     * Создаем rest-прокси для RequestService.
     *
     * @param handler
     * @return rest-proxy
     */
    @Bean
    public JobService jobService(final RestServiceInvocationHandler handler) {
        handler.setServiceUrl("/job");
        return getProxy(handler, JobService.class);
    }


    private <T> T getProxy(final RestServiceInvocationHandler handler, final Class<T>... tClass) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), tClass, handler);
    }

}
