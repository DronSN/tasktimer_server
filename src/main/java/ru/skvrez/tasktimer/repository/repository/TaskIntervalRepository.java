package ru.skvrez.tasktimer.repository.repository;

import org.springframework.data.repository.CrudRepository;
import ru.skvrez.tasktimer.repository.entity.TaskInterval;
import java.util.List;

public interface TaskIntervalRepository extends CrudRepository<TaskInterval, Integer> {

    List<TaskInterval> findByTaskIdAndFinishIsNull(Integer id);

    List<TaskInterval> findByTaskIdOrderByStartAsc(Integer id);
}
