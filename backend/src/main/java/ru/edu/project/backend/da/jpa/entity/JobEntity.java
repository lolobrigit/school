package ru.edu.project.backend.da.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import ru.edu.project.backend.api.jobs.JobAbstract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "JOB_TYPE")
public class JobEntity implements JobAbstract {

    /**
     * Первичный ключ.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "job_seq")
    @SequenceGenerator(name = "job_seq", sequenceName = "jobs_id_sequence", allocationSize = 1)
    private Long id;

    /**
     * Название.
     */
    private String title;

    /**
     * Описание.
     */
    private String desc;

    /**
     * Признак активности.
     */
    private Boolean enabled;
}
