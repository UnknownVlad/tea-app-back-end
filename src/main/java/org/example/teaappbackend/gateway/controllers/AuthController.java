package org.example.teaappbackend.gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.teaappbackend.gateway.services.authentication.AuthenticationService;
import org.example.teaappbackend.gateway.users.JwtAuthenticationDto;
import org.example.teaappbackend.gateway.users.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "регистрация")
    @PostMapping("/sing-up")
    public ResponseEntity<JwtAuthenticationDto> signUp(@RequestBody @Valid UserDto request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody @Valid UserDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
