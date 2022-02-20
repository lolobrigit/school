package ru.edu.project.backend.api.requests;

public interface RequestInfoAbstract {

    /**
     * ID.
     *
     * @return id
     */
    Long getId();

    /**
     * clientId.
     *
     * @return clientId
     */
    Long getClientId();

    /**
     * status.
     *
     * @return status
     */
    ru.edu.project.backend.api.common.Status getStatus();

    /**
     * createdAt.
     *
     * @return timestamp
     */
    java.sql.Timestamp getCreatedAt();

    /**
     * plannedVisitAt.
     *
     * @return timestamp
     */
    java.sql.Timestamp getPlannedVisitAt();

    /**
     * lastActionAt.
     *
     * @return timestamp
     */
    java.sql.Timestamp getLastActionAt();

    /**
     * closedAt.
     *
     * @return timestamp
     */
    java.sql.Timestamp getClosedAt();

    /**
     * price.
     *
     * @return bigDecimal
     */
    java.math.BigDecimal getPrice();

    /**
     * comment.
     *
     * @return comment
     */
    String getComment();

    /**
     * service list.
     *
     * @return list
     */
    java.util.List<ru.edu.project.backend.api.jobs.Job> getServices();

    /**
     * action list.
     *
     * @return list
     */
    java.util.List<ru.edu.project.backend.api.action.Action> getActionHistory();

    /**
     * inventory info.
     *
     * @return list
     */
    java.util.List<ru.edu.project.backend.api.inventories.InventoryInfo> getInventoryList();

}
