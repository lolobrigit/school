package ru.edu.project.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.api.user.UserInfo;
import ru.edu.project.backend.api.user.UserService;

@Service
public class FrontendUserService implements UserDetailsService {


    /**
     * Зависимость.
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Зависимость на бэкенд сервис.
     */
    @Autowired
    private UserService userService;

    /**
     * Регистрируем пользователя.
     *
     * @param username
     * @param password
     * @param role
     * @return id
     */
    public Long insertRow(final String username, final String password, final String role) {
        return userService.register(UserInfo.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(role)
                .enabled(true)
                .build());
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

        UserInfo info = userService.loadUserByUsername(username);

        if (info.getId() < 0) {
            throw new UsernameNotFoundException("user not found");
        }


        return new UserDetailsId(
                info.getId(),
                info.getUsername(),
                info.getPassword(),
                info.getRoles()
        );
    }


}
