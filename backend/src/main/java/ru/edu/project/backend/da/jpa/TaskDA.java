package ru.edu.project.backend.da.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.edu.project.backend.da.jpa.entity.TaskEntity;
import ru.edu.project.backend.da.jpa.repository.TaskEntityRepository;
import ru.edu.project.backend.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TaskDA {

    /**
     * Зависимость.
     */
    @Autowired
    private TaskEntityRepository repository;

    /**
     * Получаем дерево задач.
     *
     * @param clientId
     * @return дерево
     */
    public List<Task> getByClient(final long clientId) {
        Map<UUID, Task> entityMap = new HashMap<>();
        List<TaskEntity> allByClientId = repository.findAllByClientId(clientId);

        allByClientId.forEach(task -> {
            if (task.getParentId() != null) {
                if (!entityMap.containsKey(task.getParentId())) {
                    entityMap.put(task.getParentId(), Task.builder()
                            .subTasks(new ArrayList<>())
                            .build());
                }

                entityMap.get(task.getParentId()).getSubTasks().add(map(task));
            } else {
                if (!entityMap.containsKey(task.getId())) {
                    entityMap.put(task.getId(), map(task));
                } else {
                    Task saved = entityMap.get(task.getId());
                    saved.setClientId(clientId);
                    saved.setValue(task.getValue());
                }
            }
        });

        return new ArrayList<>(entityMap.values());
    }

    /**
     * Сохраняем.
     *
     * @param task
     * @return task
     */
    public Task save(final Task task) {

        TaskEntity draft = new TaskEntity();
        draft.setId(UUID.randomUUID());
        draft.setClientId(task.getClientId());
        draft.setParentId(task.getParentId());
        draft.setValue(task.getValue());

        return map(repository.save(draft));
    }


    private Task map(final TaskEntity task) {
        return Task.builder()
                .id(task.getId())
                .clientId(task.getClientId())
                .value(task.getValue())
                .subTasks(new ArrayList<>())
                .build();
    }

}
