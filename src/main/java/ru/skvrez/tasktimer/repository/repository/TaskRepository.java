package ru.skvrez.tasktimer.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skvrez.tasktimer.repository.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    void deleteAllByIdIn(Iterable<Integer> ids);
}
