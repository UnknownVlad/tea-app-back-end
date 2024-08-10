package org.example.teaappbackend.gateway.mappers;

import lombok.RequiredArgsConstructor;
import org.example.teaappbackend.gateway.dtos.UserDto;
import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public User toEntity(UserDto userDto, Collection<Role> roles, boolean isEnabled) {
        return User.builder()
                .password(passwordEncoder.encode(userDto.getPassword()))
                .isEnabled(isEnabled)
                .email(userDto.getEmail())
                .username(userDto.getEmail())
                .roles(this.toMutableCollection(roles))
                .build();
    }

    private Collection<Role> toMutableCollection(Collection<Role> immutableRoles) {
        return new HashSet<>(immutableRoles);
    }


}
