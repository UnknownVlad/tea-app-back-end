package org.example.teaappbackend.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Схема ошибочного ответа")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ComplexExceptionResponseDto {

    @Schema(description = "Схема ошибочного ответа")
    ComplexErrorDto error;


}
