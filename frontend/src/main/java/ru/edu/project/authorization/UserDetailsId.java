package ru.edu.project.authorization;

import lombok.Getter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * Расширяем стандартный контейнер авторизированного клиента.
 */
@Getter
public class UserDetailsId extends User {

    /**
     * client id.
     */
    private long userId;

    /**
     * Конструктор.
     *
     * @param id
     * @param username
     * @param password
     * @param role
     */
    public UserDetailsId(final long id, final String username, final String password, final String role) {
        super(username, password, AuthorityUtils.createAuthorityList(role));
        userId = id;
    }

}
