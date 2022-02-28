package ru.edu.project.backend.da.jdbctemplate;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.da.UserDALayer;

import java.sql.ResultSet;
import java.util.HashMap;

@Service
@Profile("JDBC_TEMPLATE")
public class UserServiceDA implements UserDALayer {

    /**
     * Поле в БД.
     */
    public static final String SELECT_USER = "SELECT * FROM users WHERE username = ?";

    /**
     * Поле в БД.
     */
    public static final String ID_ATTR = "id";

    /**
     * Поле в БД.
     */
    public static final String USERNAME_ATTR = "username";

    /**
     * Поле в БД.
     */
    public static final String PASSWORD_ATTR = "password";

    /**
     * Поле в БД.
     */
    public static final String ROLES_ATTR = "roles";

    /**
     * Поле в БД.
     */
    public static final String ENABLED_ATTR = "enabled";
    /**
     * Шаблон для вставки.
     */
    private final SimpleJdbcInsert simpleInsert;


    /**
     * Шаблон jdbc.
     */
    private final JdbcTemplate template;

    /**
     * Конструктор.
     *
     * @param jdbcInsertBean
     * @param jdbcTemplateBean
     */
    public UserServiceDA(final SimpleJdbcInsert jdbcInsertBean, final JdbcTemplate jdbcTemplateBean) {
        simpleInsert = jdbcInsertBean.withTableName("users").usingGeneratedKeyColumns(ID_ATTR);
        template = jdbcTemplateBean;
    }

    /**
     * Регистрируем пользователя.
     *
     * @param userInfo
     * @return id
     */
    @Override
    public UserInfo register(final UserInfo userInfo) {
        HashMap<String, Object> args = new HashMap<>();
        args.put(USERNAME_ATTR, userInfo.getUsername());
        args.put(PASSWORD_ATTR, userInfo.getPassword());
        args.put(ROLES_ATTR, userInfo.getRoles());
        args.put(ENABLED_ATTR, true);
        Long id = simpleInsert.executeAndReturnKey(
                args
        ).longValue();
        return UserInfo.builder()
                .id(id)
                .username(userInfo.getUsername())
                .password(userInfo.getPassword())
                .roles(userInfo.getRoles())
                .enabled(true)
                .build();
    }

    /**
     * Реализация метода поиска информации о клиенте.
     *
     * @param username
     * @return userInfo
     */
    @Override
    public UserInfo findByUsername(final String username) {
        return template.query(SELECT_USER, this::map, username);
    }

    @SneakyThrows
    private UserInfo map(final ResultSet resultSet) {
        if (!resultSet.next()) {
            return null;
        }
        return UserInfo.builder()
                .id(resultSet.getLong(ID_ATTR))
                .username(resultSet.getString(USERNAME_ATTR))
                .password(resultSet.getString(PASSWORD_ATTR))
                .roles(resultSet.getString(ROLES_ATTR))
                .build();
    }


}
