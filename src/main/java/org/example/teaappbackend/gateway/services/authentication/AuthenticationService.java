package org.example.teaappbackend.gateway.services.authentication;

import lombok.RequiredArgsConstructor;
import org.example.teaappbackend.gateway.services.UserService;
import org.example.teaappbackend.gateway.services.jwt.JwtService;
import org.example.teaappbackend.gateway.users.JwtAuthenticationDto;
import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.example.teaappbackend.gateway.users.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationDto signUp(UserDto request) {
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);

        var user = User.builder()
                .username(request.getEmail())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userService.save(user, roles);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationDto(jwt);
    }

    public JwtAuthenticationDto signIn(UserDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getEmail());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationDto(jwt);
    }
}
