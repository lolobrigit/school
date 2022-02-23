package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "request_action")
public class ActionEntity {

    /**
     * Композитный ключ.
     */
    @EmbeddedId
    private ActionPk pk;

    /**
     * Сообщение.
     */
    private String message;

    @Embeddable
    @Getter
    @Setter
    public static class ActionPk implements Serializable {

        /**
         * requestId.
         */
        @Column(name = "request_id")
        private Long requestId;

        /**
         * actionId.
         */
        @Column(name = "action_id")
        private Long actionId;

        /**
         * Время создания.
         */
        private Timestamp time;
    }


}
