package org.example.teaappbackend.gateway.services.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.gateway.services.UserService;
import org.example.teaappbackend.gateway.services.jwt.JwtService;
import org.example.teaappbackend.gateway.services.verification.VerificationCodeGeneratorService;
import org.example.teaappbackend.gateway.services.verification.VerificationCodeGeneratorServiceImpl;
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
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsEnabled(false);
        user.setEmail(email);
        user.setUsername(email);

        String verificationCode = verificationCodeGeneratorService.generate();

        //todo: добавить кафка-очередь на отправку сообщений на почту
        //mailSender.sendMessage(email, "Verificate u'r account", verificationCode);

        user.setAuthCode(
                AuthCode.builder()
                        .code(passwordEncoder.encode(verificationCode))
                        .build()
        );

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
