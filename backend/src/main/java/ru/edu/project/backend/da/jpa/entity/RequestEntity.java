package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.action.Action;
import ru.edu.project.backend.api.common.Status;
import ru.edu.project.backend.api.inventories.InventoryInfo;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.api.requests.RequestInfoAbstract;
import ru.edu.project.backend.da.jpa.converter.RequestStatusConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "REQUEST")
public class RequestEntity implements RequestInfoAbstract {

    /**
     * Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "request_seq")
    @SequenceGenerator(name = "request_seq", sequenceName = "request_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * client_id.
     */
    private Long clientId;

    /**
     * status.
     */
    @Convert(converter = RequestStatusConverter.class)
    private Status status;

    /**
     * createdAt.
     */
    @Column(name = "created_time")
    private Timestamp createdAt;

    /**
     * plannedVisitAt.
     */
    @Column(name = "planned_visit_time")
    private Timestamp plannedVisitAt;

    /**
     * lastActionAt.
     */
    @Column(name = "last_action_time")
    private Timestamp lastActionAt;

    /**
     * closedAt.
     */
    @Column(name = "closed_time")
    private Timestamp closedAt;

    /**
     * price.
     */
    private BigDecimal price;

    /**
     * comment.
     */
    private String comment;


    /**
     * service list.
     *
     * @return list
     */
    @Override
    public List<Job> getServices() {
        return null;
    }

    /**
     * action list.
     *
     * @return list
     */
    @Override
    public List<Action> getActionHistory() {
        return null;
    }

    /**
     * inventory info.
     *
     * @return list
     */
    @Override
    public List<InventoryInfo> getInventoryList() {
        return null;
    }
}
