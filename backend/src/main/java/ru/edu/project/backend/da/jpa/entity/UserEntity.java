package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@Getter
@Setter
public class UserEntity {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_id_sequence", allocationSize = 1)
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
     * Roles.
     */
    private String roles;


    /**
     * Enabled.
     */
    private Boolean enabled;
}
