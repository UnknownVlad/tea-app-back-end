package org.example.teaappbackend.gateway.services;

import lombok.RequiredArgsConstructor;
import org.example.teaappbackend.exceptions.UserExistsException;
import org.example.teaappbackend.exceptions.UserNotExistsException;
import org.example.teaappbackend.gateway.repositories.UserRepository;
import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.USER_EXISTS_EXCEPTION_MESSAGE;
import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.USER_NOT_EXISTS_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User save(User user){
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);

        return save(user, roles);
    }
    public User save(User user, Collection<Role> roles) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserExistsException(USER_EXISTS_EXCEPTION_MESSAGE);

        user.setRoles(roles);
        user.setIsEnabled(false);
        return userRepository.save(user);
    }

    public User findByEmail(String email){
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
