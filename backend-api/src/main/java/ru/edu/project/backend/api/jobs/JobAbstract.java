package ru.edu.project.backend.api.jobs;

public interface JobAbstract {

    /**
     * id.
     *
     * @return long
     */
    Long getId();

    /**
     * title.
     *
     * @return string
     */
    String getTitle();

    /**
     * desc.
     *
     * @return string
     */
    String getDesc();
}
