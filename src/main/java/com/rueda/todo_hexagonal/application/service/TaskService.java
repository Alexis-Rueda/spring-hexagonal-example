package com.rueda.todo_hexagonal.application.service;

import com.rueda.todo_hexagonal.application.port.in.*;
import com.rueda.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.rueda.todo_hexagonal.domain.exception.TaskNotFoundException;
import com.rueda.todo_hexagonal.domain.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService implements CreateTaskUseCase,
        ListTasksUseCase,
        GetTaskUseCase,
        CompleteTaskUseCase,
        ReopenTaskUseCase {

    private final TaskRepositoryPort taskRepositoryPort;

    @Override
    public Task create(Task task) {
        return taskRepositoryPort.save(task);
    }

    @Override
    public List<Task> listAll() {
        return taskRepositoryPort.findAll();
    }

    @Override
    public Task getById(Long id) {
        return taskRepositoryPort.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task complete(Long taskId) {
        Task task = taskRepositoryPort.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.complete();
        return taskRepositoryPort.update(task);
    }

    @Override
    public Task reopen(Long taskId) {
        Task task = taskRepositoryPort.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
        task.reopen();
        return taskRepositoryPort.update(task);
    }
}
