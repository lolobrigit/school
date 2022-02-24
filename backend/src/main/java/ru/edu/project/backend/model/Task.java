package ru.edu.project.backend.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Jacksonized
@Builder
public class Task {

    /**
     * Поле.
     */
    private UUID id;

    /**
     * Поле.
     */
    private Long clientId;

    /**
     * Поле.
     */
    private String value;

    /**
     * Поле.
     */
    private List<Task> subTasks;

    /**
     * Поле.
     */
    private UUID parentId;

}
