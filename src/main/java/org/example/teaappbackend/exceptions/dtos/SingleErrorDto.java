package org.example.teaappbackend.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Единичная ошибка")
public class SingleErrorDto {
    @Schema(description = "Код ошибки")
    String code;
    @Schema(description = "Сообщение об ошибке")
    String message;
}
