package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "task")
public class TaskEntity {

    /**
     * Поле.
     */
    @Id
    private UUID id;

    /**
     * Поле.
     */
    @Column(name = "client_id")
    private Long clientId;

    /**
     * Поле.
     */
    @Column(name = "parent_id")
    private UUID parentId;

    /**
     * Поле.
     */
    @Column(name = "some_text")
    private String value;
}
