package ru.skvrez.tasktimer.repository.converter;

import ru.skvrez.tasktimer.repository.entity.TaskColor;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TaskColorConverter implements AttributeConverter<TaskColor, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TaskColor taskColor) {
        if (taskColor == null) {
            return null;
        } else {
            return taskColor.getId();
        }
    }

    @Override
    public TaskColor convertToEntityAttribute(Integer id) {
        return TaskColor.fromId(id);
    }
}
