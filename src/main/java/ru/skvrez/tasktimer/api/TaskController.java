package ru.skvrez.tasktimer.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.skvrez.tasktimer.service.model.create.TaskCreateDto;
import ru.skvrez.tasktimer.service.model.get.TaskGetDto;
import ru.skvrez.tasktimer.service.model.update.TaskUpdateDto;
import ru.skvrez.tasktimer.service.service.TaskService;

import javax.websocket.server.PathParam;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private TaskService taskService;

    @Autowired
    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping()
    public List<TaskGetDto> allTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskGetDto createTask(@RequestBody TaskCreateDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskGetDto updateTask(@RequestBody TaskUpdateDto taskDto) {
        return taskService.updateTask(taskDto);
    }

    @PutMapping("/start/{id}")
    public TaskGetDto startTask(@PathVariable Integer id) {
        return taskService.startTask(id);
    }

    @PutMapping("/pause/{id}")
    public TaskGetDto pauseTask(@PathVariable Integer id) {
        return taskService.pauseTask(id);
    }

    @PutMapping("/stop/{id}")
    public TaskGetDto stopTask(@PathVariable Integer id) {
        return taskService.stopTask(id);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@PathParam("ids") Integer[] ids) {
        taskService.deleteAllTask(Arrays.asList(ids));
    }

}