package ru.skvrez.tasktimer.service.model.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.skvrez.tasktimer.service.deserializer.TaskTagsDeserializer;
import ru.skvrez.tasktimer.service.model.base.AbstractTaskDto;
import ru.skvrez.tasktimer.service.model.base.TagDto;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class
TaskCreateDto extends AbstractTaskDto {

    private List<TagDto> tags;

    @JsonDeserialize(using = TaskTagsDeserializer.class)
    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    @JsonCreator
    public static List<TagDto> fromValue(String[] source) {
        return Arrays.stream(source).map(TagDto::new).collect(Collectors.toList());
    }
}
