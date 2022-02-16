package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.api.jobs.Job;
import ru.edu.project.backend.api.jobs.JobService;
import ru.edu.project.backend.service.JobServiceLayer;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController implements JobService {

    /**
     * Делегат для передачи вызова.
     */
    @Autowired
    private JobServiceLayer delegate;

    /**
     * Получение доступных услуг.
     *
     * @return список
     */
    @Override
    @GetMapping("/getAvailable")
    public List<Job> getAvailable() {
        return delegate.getAvailable();
    }

    /**
     * Получение услуг по коду.
     *
     * @param ids
     * @return список
     */
    @Override
    @PostMapping("/getByIds")
    public List<Job> getByIds(final @RequestBody List<Long> ids) {
        return delegate.getByIds(ids);
    }
}
