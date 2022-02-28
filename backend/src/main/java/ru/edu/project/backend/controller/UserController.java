package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.api.user.UserService;
import ru.edu.project.backend.da.UserDALayer;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/user")
public class UserController implements UserService {

    /**
     * Пустой пользователь.
     */
    public static final UserInfo EMPTY_USER = UserInfo.builder().id(-1L).build();

    /**
     * Зависимость сразу на DA слой. Логику тут не применяем.
     */
    @Autowired
    private UserDALayer userServiceDA;


    /**
     * Регистрация пользователя.
     *
     * @param userInfo
     * @return
     */
    @Override
    @PostMapping("/register")
    public Long register(@RequestBody final UserInfo userInfo) {
        UserInfo user = userServiceDA.register(userInfo);
        if (user == null) {
            throw new IllegalStateException("не удалось зарегистрироваться");
        }
        return user.getId();
    }

    /**
     * Получение данных о пользователе.
     *
     * @param username
     * @return
     */
    @Override
    @GetMapping("/loadUserByUsername/{username}")
    public UserInfo loadUserByUsername(@PathVariable("username") final String username) {
        return ofNullable(userServiceDA.findByUsername(username)).orElse(EMPTY_USER);
    }
}
