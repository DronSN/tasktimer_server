package ru.skvrez.tasktimer.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skvrez.tasktimer.repository.entity.TaskDuration;

public interface TaskDurationRepository extends JpaRepository<TaskDuration, Integer> {
}
