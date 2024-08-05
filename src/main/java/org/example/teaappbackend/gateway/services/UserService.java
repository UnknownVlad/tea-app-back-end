package org.example.teaappbackend.gateway.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.exceptions.UserExistsException;
import org.example.teaappbackend.exceptions.UserNotExistsException;
import org.example.teaappbackend.gateway.repositories.UserRepository;
import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.USER_EXISTS_EXCEPTION_MESSAGE;
import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;


    public User save(User user){
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserExistsException(USER_EXISTS_EXCEPTION_MESSAGE);
        return userRepository.save(user);
    }
    public User save(User user, Collection<Role> roles) {
        log.debug("Пробую сохранить пользователя: {}, с ролями: {}", user, roles);

        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserExistsException(USER_EXISTS_EXCEPTION_MESSAGE);
        user.setRoles(roles);
        log.debug("У меня вообще-то почта: {}", user.getEmail());

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotExistsException(USER_NOT_EXISTS_EXCEPTION_MESSAGE));
    }

    public UserDetailsService userDetailsService() {
        return this::findByEmail;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByEmail(username);
    }
}
