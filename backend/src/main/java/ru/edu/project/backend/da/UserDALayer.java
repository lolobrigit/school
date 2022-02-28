package ru.edu.project.backend.da;

import ru.edu.project.backend.api.user.UserInfo;

public interface UserDALayer {

    /**
     * Регистрация пользователя.
     *
     * @param userInfo
     * @return userInfo
     */
    UserInfo register(UserInfo userInfo);

    /**
     * Поиск пользователя.
     *
     * @param username
     * @return userInfo
     */
    UserInfo findByUsername(String username);
}
