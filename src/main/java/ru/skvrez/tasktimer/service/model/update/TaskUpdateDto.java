package ru.skvrez.tasktimer.service.model.update;

import com.fasterxml.jackson.annotation.JsonCreator;
import ru.skvrez.tasktimer.service.model.base.AbstractTaskDto;
import ru.skvrez.tasktimer.service.model.base.TagDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskUpdateDto extends AbstractTaskDto {

    private Integer id;
    private List<TagDto> tags;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
