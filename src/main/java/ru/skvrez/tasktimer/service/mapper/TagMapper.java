package ru.skvrez.tasktimer.service.mapper;

import org.mapstruct.Mapper;
import ru.skvrez.tasktimer.repository.entity.Tag;
import ru.skvrez.tasktimer.service.model.base.TagDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto toDto(Tag tag);

    List<TagDto> toDtoList(List<Tag> tag);

    Tag toTag(TagDto tagDto);

    List<Tag> toTagList(List<TagDto> tagDto);

}
