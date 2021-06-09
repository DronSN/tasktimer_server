package ru.skvrez.tasktimer.repository.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.skvrez.tasktimer.repository.entity.Task;

public interface TaskRepository extends PagingAndSortingRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    void deleteAllByIdIn(Iterable<Integer> ids);

}
