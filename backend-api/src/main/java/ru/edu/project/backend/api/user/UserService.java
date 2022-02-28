package ru.edu.project.backend.api.user;

import ru.edu.project.backend.api.common.AcceptorArgument;

/**
 * Интерфейс работы с пользователями.
 */
public interface UserService {

    /**
     * Регистрация пользователя.
     *
     * @param userInfo
     * @return id
     */
    @AcceptorArgument
    Long register(UserInfo userInfo);

    /**
     * Получение данных о пользователе.
     *
     * @param username
     * @return username
     */
    UserInfo loadUserByUsername(String username);
}
