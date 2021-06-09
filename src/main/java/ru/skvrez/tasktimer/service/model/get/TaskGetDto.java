package ru.skvrez.tasktimer.service.model.get;

import ru.skvrez.tasktimer.repository.entity.TaskStatus;
import ru.skvrez.tasktimer.service.model.base.AbstractTaskDto;
import java.time.LocalDateTime;
import java.util.List;

public class TaskGetDto extends AbstractTaskDto {

    private Integer id;
    private List<String> tags;
    private TaskStatus status;
    private LocalDateTime start;
    private LocalDateTime stop;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getStop() {
        return stop;
    }

    public void setStop(LocalDateTime stop) {
        this.stop = stop;
    }
}
