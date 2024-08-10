package org.example.teaappbackend.controllers;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Schema(description = "Ответ на запрос доступа к ресурсу")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestResourceDto {

    @Schema(description = "Результат доступа к ресурсу", example = "Успешно")
    String message;
}
