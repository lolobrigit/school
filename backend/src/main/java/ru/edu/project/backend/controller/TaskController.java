package ru.edu.project.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.project.backend.da.jpa.TaskDA;
import ru.edu.project.backend.model.Task;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    /**
     * Зависимость.
     */
    @Autowired
    private TaskDA da;

    /**
     * Получаем дерево по клиенту.
     *
     * @param clientId
     * @return список
     */
    @GetMapping("/get/{clientId}")
    public List<Task> get(@PathVariable("clientId") final Long clientId) {
        return da.getByClient(clientId);
    }

    /**
     * Сохраняем привязку.
     *
     * @param task
     * @return объект
     */
    @PostMapping("/save")
    public Task save(@RequestBody final Task task) {
        return da.save(task);
    }
}
