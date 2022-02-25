package ru.edu.project.app;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;

@Configuration
public class TomcatConfig {

    /**
     * Конфигурация jndi для встроенного Tomcat.
     *
     * @return tomcatServletWebServerFactory
     */
    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory() {
        return new TomcatServletWebServerFactory() {

            /**
             * Активация jndi.
             *
             * @param tomcat
             * @return tomcat
             */
            @Override
            protected TomcatWebServer getTomcatWebServer(final Tomcat tomcat) {
                tomcat.enableNaming();
                return super.getTomcatWebServer(tomcat);
            }

            /**
             * Создание ресурса datasource.
             *
             * @param context
             */
            @Override
            protected void postProcessContext(final Context context) {
                ContextResource resource = new ContextResource();
                resource.setName("jdbc/frontendDbLink");
                resource.setType(DataSource.class.getName());

                //Можно вынести в свойства или оставить константы как есть
                //так как это применяется только для локального запуска
                //на внешнем сервере развернутый war будет использовать
                //ресурсы сервера
                resource.setProperty("factory", "com.zaxxer.hikari.HikariJNDIFactory");
                resource.setProperty("driverClassName", "org.h2.Driver");
                resource.setProperty("jdbcUrl", "jdbc:h2:./frontend_db");
                resource.setProperty("username", "sa");
                resource.setProperty("password", "");

                context.getNamingResources()
                        .addResource(resource);
            }
        };
    }

    /**
     * Извлечение jndi datasource.
     *
     * @return datasource
     * @throws IllegalArgumentException
     * @throws NamingException
     */
    @Bean(destroyMethod = "")
    public DataSource jndiDataSource() throws IllegalArgumentException, NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName("java:comp/env/jdbc/frontendDbLink");
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }

}
