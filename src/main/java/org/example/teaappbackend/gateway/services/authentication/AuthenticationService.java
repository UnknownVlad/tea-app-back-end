package org.example.teaappbackend.gateway.services.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.gateway.dtos.JwtAuthenticationDto;
import org.example.teaappbackend.gateway.dtos.UserDto;
import org.example.teaappbackend.gateway.mappers.AuthCodeMapper;
import org.example.teaappbackend.gateway.mappers.UserMapper;
import org.example.teaappbackend.gateway.services.UserService;
import org.example.teaappbackend.gateway.services.jwt.JwtService;
import org.example.teaappbackend.gateway.services.verification.VerificationCodeGeneratorService;
import org.example.teaappbackend.gateway.users.AuthCode;
import org.example.teaappbackend.gateway.users.Role;
import org.example.teaappbackend.gateway.users.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeGeneratorService verificationCodeGeneratorService;
    private final UserMapper userMapper;
    private final AuthCodeMapper authCodeMapper;

    @Transactional
    public JwtAuthenticationDto signUp(UserDto request) {
        log.debug("Пробую зарегестрировать пользователя: {}", request);

        User user = userMapper.toEntity(request, Set.of(Role.ROLE_USER), false);

        AuthCode authCode = authCodeMapper.toEntity(verificationCodeGeneratorService.generate(), false, user);

        user.setAuthCode(authCode);

        userService.save(user);

        return new JwtAuthenticationDto(jwtService.generateToken(user));
    }

    @Transactional
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
