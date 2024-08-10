package org.example.teaappbackend.gateway.mappers;

import org.example.teaappbackend.gateway.users.AuthCode;
import org.example.teaappbackend.gateway.users.User;
import org.springframework.stereotype.Component;


@Component
public class AuthCodeMapper {
    public AuthCode toEntity(String code, boolean isSent, User user){
        return AuthCode.builder()
                .code(code)
                .isSent(isSent)
                .user(user)
                .build();
    }
}
