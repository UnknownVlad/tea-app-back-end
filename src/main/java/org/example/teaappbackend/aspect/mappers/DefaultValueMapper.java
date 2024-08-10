package org.example.teaappbackend.aspect.mappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collection;
import java.util.Map;

public class DefaultValueMapper implements ValueMapper {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Collection<Map<String, Object>> toMap(Object... objects) {
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(objects), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
