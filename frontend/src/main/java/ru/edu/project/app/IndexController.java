package ru.edu.project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.project.authorization.UserServiceDa;

@Controller
public class IndexController {

    /**
     * Минимальная длина пароля.
     */
    public static final int MIN_PASS_LENGTH = 4;

    /**
     * Зависимость.
     */
    @Autowired
    private UserServiceDa userServiceDa;

    /**
     * Точка входа.
     *
     * @return view
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Форма регистрации.
     *
     * @return viewname
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * Обработка формы регистрации.
     *
     * @param username
     * @param password
     * @param password2
     * @param role
     * @return redirect
     */
    @PostMapping("/register")
    public String registerProcess(
            @RequestParam(name = "username") final String username,
            @RequestParam(name = "password") final String password,
            @RequestParam(name = "password2") final String password2,
            @RequestParam(name = "role") final String role
    ) {
        String userRole = "ROLE_CLIENT";
        if ("manager".equals(role)) {
            userRole = "ROLE_MANAGER";
        }

        if (!password.equals(password2) || password.length() < MIN_PASS_LENGTH) {
            return "redirect:/login?bad_password";
        }

        try {
            userServiceDa.insertRow(username, password, userRole);
        } catch (Exception e) {
            return "redirect:/register?invalid_request";
        }
        return "redirect:/login";
    }
}
