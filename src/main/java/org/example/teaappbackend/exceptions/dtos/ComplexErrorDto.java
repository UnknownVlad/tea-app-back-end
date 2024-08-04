package org.example.teaappbackend.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Схема комплексного ответа")
public class ComplexErrorDto {
    @Schema(description = "Код ошибки", example = "404")
    String code;
    @Schema(description = "Сообщение ошибки", example = "Пользовтель с таким email не найден")
    String message;

    @Schema(description = "Дополнительная информация об ошибке")
    AdditionalInfoDto additionalInfo;

}
