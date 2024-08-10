package org.example.teaappbackend.gateway.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@Schema(description = "Ответ на запрос подтверждения аккаунта")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationAccountDto {

    @Schema(description = "Результат подтверждения аккаунта", example = "Код подтверждения аккаунта больше недействителен")
    String message;


    @Schema(description = "Подтвержден ли аккаунт", example = "false")
    Boolean isActivated;
}
