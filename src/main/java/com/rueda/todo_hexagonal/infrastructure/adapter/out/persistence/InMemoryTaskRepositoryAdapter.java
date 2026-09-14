package com.rueda.todo_hexagonal.infrastructure.adapter.out.persistence;

import com.rueda.todo_hexagonal.application.port.out.TaskRepositoryPort;
import com.rueda.todo_hexagonal.domain.model.Task;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Primary
public class InMemoryTaskRepositoryAdapter implements TaskRepositoryPort {

    private final Map<Long, Task> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);


    @Override
    public Task save(Task task) {

        task.initDefaults();

        if(task.getId()==null){
            Task withId = Task.builder()
                    .id(idSequence.getAndIncrement())
                    .title(task.getTitle())
                    .description(task.getDescription())
                    .status(task.getStatus())
                    .createdAt(task.getCreatedAt())
                    .completedAt(task.getCompletedAt())
                    .build();
            store.put(withId.getId(), withId);
            return withId;
        }

        store.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Task> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Task update(Task task) {
        store.put(task.getId(), task);
        return task;
    }
}
