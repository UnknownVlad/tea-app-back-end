package org.example.teaappbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.teaappbackend.exceptions.dtos.ComplexExceptionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Tag(name = "Рест ручки для тестирования")
@Slf4j
public class TestController {

    @Operation(summary = "добраться до ресурса")
    @GetMapping("/get-resource")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный доступ к ресурсу",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TestResourceDto.class))),
            @ApiResponse(responseCode = "400", description = "Неверный запрос",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ComplexExceptionResponseDto.class)))
    })
    public ResponseEntity<TestResourceDto> test() {
        return ResponseEntity.ok(TestResourceDto.builder()
                .message("Успешно")
                .build());
    }
}
