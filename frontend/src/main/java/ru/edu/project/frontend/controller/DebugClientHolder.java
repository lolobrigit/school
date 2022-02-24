package ru.edu.project.frontend.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DebugClientHolder {

    /**
     * Отладочный id клиента.
     */
    private volatile long stubClientId = 1L;

}
