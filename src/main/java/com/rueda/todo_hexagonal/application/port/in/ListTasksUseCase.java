package com.rueda.todo_hexagonal.application.port.in;

import com.rueda.todo_hexagonal.domain.model.Task;
import java.util.List;

public interface ListTasksUseCase {
    List<Task> listAll();
}
