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
@Schema(description = "Запрос на подтверждение аккаунта")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationCodeDto {
    @Schema(description = "Почта пользовтеля", example = "sofia.kage@mail.ru")
    @NotBlank(message = "Почта пользователя не может быть пустой")
    @Size(min = 5, max = 255, message = "Почта пользовтаеля должна содержать от 5 до 255 символов")
    @Email(message = "Невалидный формать почты")
    private String email;

    //todo: добавить мб более сложную валидацию пароля
    @Schema(description = "Пароль пользователя", example = "password1029Laq!")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть не менее 8 символов, и не более 255")
    private String password;
}
