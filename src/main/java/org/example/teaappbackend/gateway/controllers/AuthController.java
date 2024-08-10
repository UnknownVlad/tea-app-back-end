package org.example.teaappbackend.gateway.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.aspect.Logged;
import org.example.teaappbackend.aspect.ejectors.AuthKeyEjector;
import org.example.teaappbackend.exceptions.dtos.ComplexExceptionResponseDto;
import org.example.teaappbackend.gateway.dtos.ConfirmationAccountDto;
import org.example.teaappbackend.gateway.services.authentication.AuthenticationService;
import org.example.teaappbackend.gateway.dtos.JwtAuthenticationDto;
import org.example.teaappbackend.gateway.dtos.UserDto;
import org.example.teaappbackend.gateway.services.confirmation.ConfirmationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Аутентификация")
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;
    private final ConfirmationService confirmationService;

    @Logged(keyEjector = AuthKeyEjector.class)
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
    @Logged(keyEjector = AuthKeyEjector.class)
    @Operation(summary = "Авторизация пользователя")
    @PostMapping("/sign-in")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComplexExceptionResponseDto.class)))
    })
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody @Valid UserDto request) {
        log.debug("Пришел запрос на аутентификацию с следующими параметрами: {}", request);
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    //todo: добавить валидацию кода
    @Operation(summary = "Подтверждение аккаунта пользователя")
    @PostMapping("/confirm/{code}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная авторизация",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ConfirmationAccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComplexExceptionResponseDto.class)))
    })
    public ResponseEntity<ConfirmationAccountDto> confirmationAccount(@PathVariable("code") @NotBlank String code) {
        log.debug("Попытка подтвердить аккаунт ");
        return ResponseEntity.ok(confirmationService.confirmAccount(code));
    }


}
