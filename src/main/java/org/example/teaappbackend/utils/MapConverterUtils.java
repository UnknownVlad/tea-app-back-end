package org.example.teaappbackend.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Map;

public class MapConverterUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Object> objectToMap(Object... objects) {
        System.out.println("start");
        try {
            return objectMapper.readValue(objectMapper.writeValueAsString(objects), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
