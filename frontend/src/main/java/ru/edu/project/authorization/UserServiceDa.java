package ru.edu.project.authorization;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.HashMap;

@Service
public class UserServiceDa implements UserDetailsService {

    /**
     * Шаблон для вставки.
     */
    private SimpleJdbcInsert simpleInsert;

    /**
     * Зависимость.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Зависимость на шаблон.
     */
    @Autowired
    private JdbcTemplate template;

    /**
     * Конструктор.
     *
     * @param dataSource
     */
    public UserServiceDa(final DataSource dataSource) {
        this.simpleInsert = new SimpleJdbcInsert(dataSource).withTableName("users").usingGeneratedKeyColumns("id");
    }

    /**
     * Регистрируем пользователя.
     *
     * @param username
     * @param password
     * @param role
     * @return id
     */
    public Long insertRow(final String username, final String password, final String role) {
        HashMap<String, Object> args = new HashMap<>();
        args.put("username", username);
        args.put("password", "{bcrypt}" + passwordEncoder.encode(password));
        args.put("roles", role);
        args.put("enabled", true);
        return simpleInsert.executeAndReturnKey(
                args
        ).longValue();
    }

    /**
     * Реализация метода поиска информации о клиенте.
     *
     * @param username
     * @return userDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        UserDetailsId user = null;
        try {
            user = template.query("SELECT * FROM users WHERE username = ?", this::map, username);
        } catch (Exception e) {

        }
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }

    @SneakyThrows
    private UserDetailsId map(final ResultSet resultSet) {
        resultSet.next();
        return new UserDetailsId(
                resultSet.getLong("id"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("roles")
        );
    }


}
