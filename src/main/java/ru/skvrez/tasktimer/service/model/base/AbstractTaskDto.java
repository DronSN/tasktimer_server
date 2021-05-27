package ru.skvrez.tasktimer.service.model.base;

import ru.skvrez.tasktimer.repository.entity.TaskColor;

public class AbstractTaskDto {

    private String name;
    private TaskColor color;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskColor getColor() {
        return color;
    }

    public void setColor(TaskColor color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
