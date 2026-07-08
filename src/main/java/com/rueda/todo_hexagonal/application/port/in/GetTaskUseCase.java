package com.rueda.todo_hexagonal.application.port.in;

import com.rueda.todo_hexagonal.domain.model.Task;

public interface GetTaskUseCase {
    Task getById(Long id);
}
