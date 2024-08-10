package org.example.teaappbackend.aspect.ejectors;

import org.example.teaappbackend.gateway.dtos.UserDto;

import java.util.Map;

public class AuthKeyEjector implements KeyEjector {
    @Override
    public Map<String, Object> eject(Object... objects) {
        if (objects.length > 1)
            return Map.of();

        UserDto userDto = (UserDto) objects[0];

        return Map.of("email", userDto.getEmail());
    }
}
