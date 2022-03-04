package ru.edu.project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.edu.project.authorization.FrontendUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Зависимость на реализацию UserDetailService.
     */
    @Autowired
    private FrontendUserService frontendUserService;

    /**
     * Настраиваем параметры доступов к URL.
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .anonymous()
                .authorities("ROLE_ANON")
                .and()
                .authorizeHttpRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/*").hasAuthority("ROLE_ANON")
                .antMatchers("/client/**").hasAuthority("ROLE_CLIENT")
                .antMatchers("/manager/**").hasAuthority("ROLE_MANAGER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .csrf().disable(); //отключаем защиту от Cross-Site Request Forgery

        /*
         * Если не отключать csrf, под каждую форму придется добавлять токен вида:
         * <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
         */


        // При необходимости можно кастомизировать страницу с формой логина и адрес обработчика этой формы
        // .loginPage("/login").loginProcessingUrl("/process")

    }

    /**
     * Настраиваем пользователей.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        /*
        для быстрой отладки можно использовать inMemory реестр пользователей:

        auth
                .inMemoryAuthentication()
                .withUser("manager")
                .password(encoder.encode("manager"))
                .roles("MANAGER")
                .and()
                .withUser("cleint")
                .password(encoder.encode("cleint"))
                .roles("CLIENT");
        */
        /*
        базовый пример доступа к таблице с пользователями через JDBC:
        auth.jdbcAuthentication()
                .passwordEncoder(passwordEncoder)
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, roles FROM users WHERE username = ?")

        */

        auth.userDetailsService(frontendUserService);

    }


}
