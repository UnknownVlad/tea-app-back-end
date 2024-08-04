package org.example.teaappbackend.exceptions.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Дополнительная информация об ошибке")
public class AdditionalInfoDto {
    @Schema(description = "Список ошибок")
    List<SingleErrorDto> errors;
}
