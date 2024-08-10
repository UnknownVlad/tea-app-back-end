package org.example.teaappbackend.aspect.ejectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

//todo: подумать над тем, как удобно реализовать
public class DefaultKeyEjector implements KeyEjector{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Map<String, Object> eject(Object... objects) {
        return null;
    }
}
