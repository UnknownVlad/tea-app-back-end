package org.example.teaappbackend.gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.exceptions.dtos.ComplexExceptionResponseDto;
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
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @Operation(summary = "регистрация")
    @PostMapping("/sing-up")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComplexExceptionResponseDto.class)))
    })
    public ResponseEntity<JwtAuthenticationDto> signUp(@RequestBody @Valid UserDto request) {
        log.debug("Пришел запрос на регистрацию с следующими параметрами: {}", request);
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComplexExceptionResponseDto.class)))
    })
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody @Valid UserDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/confirm")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComplexExceptionResponseDto.class)))
    })
    public ResponseEntity<JwtAuthenticationDto> confirmationAccount(@RequestBody @Valid UserDto request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }


}
