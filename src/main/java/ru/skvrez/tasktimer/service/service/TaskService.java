package ru.skvrez.tasktimer.service.service;

import ru.skvrez.tasktimer.service.model.base.PageModel;
import ru.skvrez.tasktimer.service.model.create.TaskCreateDto;
import ru.skvrez.tasktimer.service.model.get.TaskGetDto;
import ru.skvrez.tasktimer.service.model.update.TaskUpdateDto;
import ru.skvrez.tasktimer.specification.FilterConstraint;
import java.util.List;

public interface TaskService {

    List<TaskGetDto> getAllTasks();

    PageModel<TaskGetDto> getAllTasks(int page, int pageSize, String sortField, String sortOrder, List<FilterConstraint> filter);

    TaskGetDto createTask(TaskCreateDto taskDto);

    TaskGetDto updateTask(TaskUpdateDto taskDto);

    TaskGetDto startTask(Integer id);

    TaskGetDto pauseTask(Integer id);

    TaskGetDto stopTask(Integer id);

    void deleteTask(Integer id);

    void deleteAllTask(List<Integer> ids);
}
