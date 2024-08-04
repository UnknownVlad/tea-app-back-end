package org.example.teaappbackend.exceptions.handler;

import org.example.teaappbackend.exceptions.constants.ExceptionConstants;
import org.example.teaappbackend.exceptions.dtos.AdditionalInfoDto;
import org.example.teaappbackend.exceptions.dtos.ComplexErrorDto;
import org.example.teaappbackend.exceptions.dtos.ComplexExceptionResponseDto;
import org.example.teaappbackend.exceptions.dtos.SingleErrorDto;
import org.example.teaappbackend.gateway.controllers.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice(assignableTypes = {
        AuthController.class
})
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleValidationExceptions( MethodArgumentNotValidException ex) {

        ComplexErrorDto complexErrorDto = ComplexErrorDto.builder()
                .code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(ExceptionConstants.VALIDATION_EXCEPTION_MESSAGE)
                .additionalInfo(
                        AdditionalInfoDto.builder()
                                .errors(
                                        ex.getAllErrors().stream()
                                                .map(objectError -> SingleErrorDto.builder()
                                                        .code(objectError.getCode())
                                                        .message(objectError.getDefaultMessage())
                                                        .build()
                                                )
                                                .toList()
                                )
                                .build()
                )
                .build();


        return new ResponseEntity<>(ComplexExceptionResponseDto.builder()
                .error(complexErrorDto)
                .build(),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleValidationExceptions(Exception ex) {

        ComplexErrorDto complexErrorDto = ComplexErrorDto.builder()
                .code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .message(ex.getMessage())
                .build();


        return new ResponseEntity<>(ComplexExceptionResponseDto.builder()
                .error(complexErrorDto)
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
