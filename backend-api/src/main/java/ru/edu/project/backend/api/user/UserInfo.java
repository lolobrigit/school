package ru.edu.project.backend.api.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Jacksonized
@Builder
public class UserInfo {

    /**
     * UserId.
     */
    private Long id;

    /**
     * Username.
     */
    private String username;


    /**
     * Password.
     */
    private String password;

    /**
     * Role.
     */
    private String roles;

    /**
     * Enabled.
     */
    private Boolean enabled;

}
