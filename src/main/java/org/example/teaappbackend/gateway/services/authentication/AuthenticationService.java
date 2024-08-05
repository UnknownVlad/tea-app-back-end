package org.example.teaappbackend.gateway.services.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.gateway.dtos.JwtAuthenticationDto;
import org.example.teaappbackend.gateway.services.UserService;
import org.example.teaappbackend.gateway.services.jwt.JwtService;
import org.example.teaappbackend.gateway.services.verification.VerificationCodeGeneratorService;
import org.example.teaappbackend.gateway.users.*;
import org.example.teaappbackend.mail.sender.MailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeGeneratorService verificationCodeGeneratorService;
    private final MailSender mailSender;

    public JwtAuthenticationDto signUp(UserDto request) {
        log.debug("Пробую зарегестрировать пользователя: {}", request);
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);

        String email = request.getEmail();
        User user = User.builder()
                .password(passwordEncoder.encode(request.getPassword()))
                .isEnabled(false)
                .email(request.getEmail())
                .username(request.getEmail())
                .roles(roles)
                .build();

        String verificationCode = verificationCodeGeneratorService.generate();

        AuthCode authCode = AuthCode.builder()
                .code(verificationCode)
                .isSent(false)
                .user(user)
                .build();

        user.setAuthCode(authCode);

        userService.save(user);
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
