package ru.skvrez.tasktimer.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.skvrez.tasktimer.repository.entity.Tag;
import ru.skvrez.tasktimer.repository.entity.Task;
import ru.skvrez.tasktimer.repository.entity.TaskDuration;
import ru.skvrez.tasktimer.service.model.create.TaskCreateDto;
import ru.skvrez.tasktimer.service.model.get.TaskDurationGetDto;
import ru.skvrez.tasktimer.service.model.get.TaskGetDto;
import ru.skvrez.tasktimer.service.model.update.TaskUpdateDto;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "tags", source = "tags", qualifiedByName = "mapTags")
    TaskGetDto toGetDto(Task task);

    List<TaskGetDto> toGetDtoList(List<Task> taskList);

    TaskDurationGetDto toGetDto(TaskDuration task);

    List<TaskDurationGetDto> toGetDetailDtoList(List<TaskDuration> taskList);


    Task toTask(TaskCreateDto taskCreateDto);

    Task toTask(TaskUpdateDto taskUpdateDto);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "tags", source = "dto.tags")
    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "color", source = "dto.color")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "start", source = "entity.start")
    @Mapping(target = "stop", source = "entity.stop")
    Task toTask(TaskUpdateDto dto, Task entity);

    @Named("mapTags")
    default List<String> mapTags(List<Tag> tag) {
        if (tag == null || tag.isEmpty()) {
            return Collections.emptyList();
        } else {
            return tag.stream().map(Tag::getName).collect(Collectors.toList());
        }
    }

}

