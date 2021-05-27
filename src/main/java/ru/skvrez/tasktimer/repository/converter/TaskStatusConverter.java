package ru.skvrez.tasktimer.repository.converter;

import ru.skvrez.tasktimer.repository.entity.TaskStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskStatus taskStatus) {
        Objects.requireNonNull(taskStatus, "Task status cannot be null set");
        return taskStatus.getId();
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer id) {
        return TaskStatus.fromId(id);
    }
}
