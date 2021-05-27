package ru.skvrez.tasktimer.service.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.skvrez.tasktimer.service.model.base.TagDto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskTagsDeserializer extends StdDeserializer<List<TagDto>> {

    protected TaskTagsDeserializer() {
        this(null);
    }

    protected TaskTagsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public List<TagDto> deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        if (jsonParser.currentToken() != JsonToken.START_ARRAY) {
            return Collections.emptyList();
        }
        List<TagDto> result = new ArrayList<>();
        while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                result.add(new TagDto(jsonParser.getText()));
        }
        return result;
    }
}
