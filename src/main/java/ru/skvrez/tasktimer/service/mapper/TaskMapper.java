package ru.skvrez.tasktimer.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skvrez.tasktimer.repository.entity.Tag;
import ru.skvrez.tasktimer.repository.entity.Task;
import ru.skvrez.tasktimer.service.model.create.TaskCreateDto;
import ru.skvrez.tasktimer.service.model.get.TaskGetDto;
import ru.skvrez.tasktimer.service.model.update.TaskUpdateDto;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    TaskGetDto toGetDto(Task task);

    List<TaskGetDto> toGetDtoList(List<Task> taskList);

    Task toTask(TaskCreateDto taskCreateDto);

    Task toTask(TaskUpdateDto taskUpdateDto);

    @Named("mapTags")
    default String[] mapTags(List<Tag> tag) {
        if (tag == null || tag.isEmpty()) {
            return new String[]{};
        } else {
            return tag.stream().map(Tag::getName).toArray(String[]::new);
        }
    }

}

