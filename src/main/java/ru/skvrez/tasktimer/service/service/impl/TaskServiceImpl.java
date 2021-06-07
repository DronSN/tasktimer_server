package ru.skvrez.tasktimer.service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skvrez.tasktimer.repository.entity.Task;
import ru.skvrez.tasktimer.repository.entity.TaskInterval;
import ru.skvrez.tasktimer.repository.entity.TaskStatus;
import ru.skvrez.tasktimer.repository.repository.TaskIntervalRepository;
import ru.skvrez.tasktimer.repository.repository.TaskRepository;
import ru.skvrez.tasktimer.service.mapper.TaskMapper;
import ru.skvrez.tasktimer.service.model.base.PageModel;
import ru.skvrez.tasktimer.service.model.create.TaskCreateDto;
import ru.skvrez.tasktimer.service.model.get.TaskGetDto;
import ru.skvrez.tasktimer.service.model.update.TaskUpdateDto;
import ru.skvrez.tasktimer.service.service.TaskService;
import ru.skvrez.tasktimer.specification.EntityFilter;
import ru.skvrez.tasktimer.specification.FilterConstraint;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskIntervalRepository taskIntervalRepository;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskIntervalRepository taskIntervalRepository,
                           TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskIntervalRepository = taskIntervalRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public List<TaskGetDto> getAllTasks() {
        List<Task> taskList = (List<Task>) taskRepository.findAll();
        return taskMapper.toGetDtoList(taskList);
    }

    @Override
    public PageModel<TaskGetDto> getAllTasks(int page, int pageSize, String sortField, String sortOrder, List<FilterConstraint> filter) {

        Sort sort = Sort.unsorted();
        if (!sortField.isEmpty()) {
            sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        }
        Pageable pageRequest = PageRequest.of(page, pageSize, sort);
        EntityFilter<Task> taskEntityFilter = new EntityFilter<>(Task.class, filter);
        Specification<Task> specification = taskEntityFilter.getSpecification();
        Page<Task> tasks = taskRepository.findAll(specification, pageRequest);
        List<TaskGetDto> tasksDto = taskMapper.toGetDtoList(tasks.getContent());
        return new PageModel<>(tasksDto, tasks.getTotalElements(), page);
    }

    @Override
    @Transactional
    public TaskGetDto createTask(TaskCreateDto taskCreateDto) {
        Task savedTask = taskRepository.save(creatingTransform(taskMapper.toTask(taskCreateDto)));
        saveStartInterval(savedTask, LocalDateTime.now());
        return taskMapper.toGetDto(savedTask);
    }

    @Override
    public TaskGetDto updateTask(TaskUpdateDto taskDto) {
        TaskStatus currentStatus = getTaskStatusById(taskDto.getId());
        Task forSavingTask = taskMapper.toTask(taskDto);
        forSavingTask.setStatus(currentStatus);
        Task savedTaskDto = taskRepository.save(taskMapper.toTask(taskDto));
        return taskMapper.toGetDto(savedTaskDto);
    }

    @Override
    @Transactional
    public TaskGetDto startTask(Integer id) {
        if (isTaskStarted(id)) {
            throw new IllegalArgumentException(String.format("Task %s is already started", id));
        }
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.STARTED);
        saveStartInterval(task, LocalDateTime.now());
        return taskMapper.toGetDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskGetDto pauseTask(Integer id) {
        List<TaskInterval> taskIntervalList = taskIntervalRepository.findByTaskIdAndFinishIsNull(id);
        if (taskIntervalList.size() != 1) {
            throw new IllegalArgumentException(String.format("Task %s can't paused", id));
        }
        TaskInterval taskInterval = taskIntervalList.get(0);
        taskInterval.setFinish(LocalDateTime.now());
        taskIntervalRepository.save(taskInterval);
        Task task = getTaskById(id);
        task.setStatus(TaskStatus.PAUSED);
        return taskMapper.toGetDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskGetDto stopTask(Integer id) {
        List<TaskInterval> taskIntervalList = taskIntervalRepository.findByTaskIdAndFinishIsNull(id);
        if (taskIntervalList.isEmpty()) {
            List<TaskInterval> taskIntervalFinished = taskIntervalRepository.findByTaskIdOrderByStartAsc(id);
            if (taskIntervalFinished.size() > 0) {
                int lastElement = taskIntervalFinished.size() - 1;
                saveIntervalFinishTime(taskIntervalFinished.get(lastElement), LocalDateTime.now());
            } else {
                throw new IllegalArgumentException(String.format("Task %s can't stopped", id));
            }
        } else if (taskIntervalList.size() == 1) {
            saveIntervalFinishTime(taskIntervalList.get(0), LocalDateTime.now());
        } else {
            throw new IllegalArgumentException(String.format("Task %s can't stopped", id));
        }
        Task task = getTaskById(id);
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

    private void saveStartInterval(Task task, LocalDateTime time) {
        TaskInterval taskInterval = new TaskInterval();
        taskInterval.setStart(time);
        taskInterval.setTask(task);
        taskIntervalRepository.save(taskInterval);
    }

    private Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Task entity by id %s not found in database", id)));
    }

    private TaskStatus getTaskStatusById(Integer id) {
        return getTaskById(id).getStatus();
    }

    private boolean isTaskStarted(Integer taskId) {
        return !taskIntervalRepository.findByTaskIdAndFinishIsNull(taskId).isEmpty();
    }

    private void saveIntervalFinishTime(TaskInterval taskInterval, LocalDateTime time) {
        taskInterval.setFinish(time);
        taskIntervalRepository.save(taskInterval);
    }}
