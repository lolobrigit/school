package ru.edu.project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.edu.project.authorization.FrontendUserService;

import java.util.Collection;

@Controller
public class IndexController {

    /**
     * Минимальная длина пароля.
     */
    public static final int MIN_PASS_LENGTH = 4;

    /**
     * Строковое представление роли клиента.
     */
    public static final String ROLE_CLIENT_STR = "ROLE_CLIENT";

    /**
     * Строковое представление роли менеджера.
     */
    public static final String ROLE_MANAGER_STR = "ROLE_MANAGER";

    /**
     * Объект роли клиента.
     */
    public static final SimpleGrantedAuthority ROLE_CLIENT = new SimpleGrantedAuthority(ROLE_CLIENT_STR);

    /**
     * Объект роли менеджера.
     */
    public static final SimpleGrantedAuthority ROLE_MANAGER = new SimpleGrantedAuthority(ROLE_MANAGER_STR);

    /**
     * Зависимость.
     */
    @Autowired
    private FrontendUserService userServiceDa;

    /**
     * Точка входа.
     *
     * @param authentication
     * @return view
     */
    @GetMapping("/")
    public String index(final Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return redirectByRole(authentication.getAuthorities());
        }
        return "index";
    }

    private String redirectByRole(final Collection<? extends GrantedAuthority> authorities) {
        if (authorities.contains(ROLE_CLIENT)) {
            return "redirect:/client/";
        }

        if (authorities.contains(ROLE_MANAGER)) {
            return "redirect:/manager/";
        }

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
        String userRole = ROLE_CLIENT_STR;
        if ("manager".equals(role)) {
            userRole = ROLE_MANAGER_STR;
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
