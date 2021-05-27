package ru.skvrez.tasktimer.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skvrez.tasktimer.repository.entity.Task;
import ru.skvrez.tasktimer.repository.entity.TaskStatus;
import ru.skvrez.tasktimer.repository.repository.TaskRepository;
import ru.skvrez.tasktimer.service.mapper.TaskMapper;
import ru.skvrez.tasktimer.service.model.create.TaskCreateDto;
import ru.skvrez.tasktimer.service.model.get.TaskGetDto;
import ru.skvrez.tasktimer.service.model.update.TaskUpdateDto;
import ru.skvrez.tasktimer.service.service.TaskService;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskGetDto> getAllTasks() {
        List<Task> taskList = taskRepository.findAll();
        return taskMapper.toGetDtoList(taskList);
    }

    @Override
    public TaskGetDto createTask(TaskCreateDto taskCreateDto) {
        Task savedTaskDto = taskRepository.save(creatingTransform(taskMapper.toTask(taskCreateDto)));
        return taskMapper.toGetDto(savedTaskDto);
    }

    @Override
    public TaskGetDto updateTask(TaskUpdateDto taskDto) {
        Task dbTask = taskRepository.getOne(taskDto.getId());
        Task forSavingTask = taskMapper.toTask(taskDto);
        forSavingTask.setStatus(dbTask.getStatus());
        Task savedTaskDto = taskRepository.save(forSavingTask);
        return taskMapper.toGetDto(savedTaskDto);
    }

    @Override
    public TaskGetDto startTask(Integer id) {
        Task task = taskRepository.getOne(id);
        return taskMapper.toGetDto(taskRepository.save(creatingTransform(task)));
    }

    @Override
    public TaskGetDto pauseTask(Integer id) {
        Task task = taskRepository.getOne(id);
        task.setStatus(TaskStatus.PAUSED);
        return taskMapper.toGetDto(taskRepository.save(task));
    }

    @Override
    public TaskGetDto stopTask(Integer id) {
        Task task = taskRepository.getOne(id);
        task.setStatus(TaskStatus.STOPPED);
        task.setStop(LocalDateTime.now());
        return taskMapper.toGetDto(taskRepository.save(task));
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllTask(List<Integer> ids) {
        taskRepository.deleteAllByIdIn(ids);
    }

    private Task creatingTransform(Task task) {
        task.setStatus(TaskStatus.STARTED);
        task.setStart(LocalDateTime.now());
        return task;
    }
}
