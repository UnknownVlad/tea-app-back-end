package org.example.teaappbackend.exceptions.handler;

import org.example.teaappbackend.exceptions.CodeNotExistsException;
import org.example.teaappbackend.exceptions.CodeTimeOutException;
import org.example.teaappbackend.exceptions.constants.ExceptionConstants;
import org.example.teaappbackend.exceptions.dtos.AdditionalInfoDto;
import org.example.teaappbackend.exceptions.dtos.ComplexErrorDto;
import org.example.teaappbackend.exceptions.dtos.ComplexExceptionResponseDto;
import org.example.teaappbackend.exceptions.dtos.SingleErrorDto;
import org.example.teaappbackend.gateway.controllers.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.example.teaappbackend.exceptions.constants.ExceptionConstants.ACCOUNT_IS_DISABLED;

@RestControllerAdvice(assignableTypes = {
        AuthController.class
})
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
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

    @ExceptionHandler(CodeNotExistsException.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleException(CodeNotExistsException ex) {
        return wrapException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(CodeTimeOutException.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleException(CodeTimeOutException ex) {
        return wrapException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleException(DisabledException ex) {
        return wrapException(HttpStatus.BAD_REQUEST, ACCOUNT_IS_DISABLED);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ComplexExceptionResponseDto> handleException(Exception ex) {
        return wrapException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private ResponseEntity<ComplexExceptionResponseDto> wrapException(HttpStatus status, String message) {
        ComplexErrorDto complexErrorDto = ComplexErrorDto.builder()
                .code(String.valueOf(status.value()))
                .message(message)
                .build();

        return new ResponseEntity<>(ComplexExceptionResponseDto.builder()
                .error(complexErrorDto)
                .build(),
                status
        );
    }
}
