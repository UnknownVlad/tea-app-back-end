package org.example.teaappbackend.gateway.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ответ с токеном доступа")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtAuthenticationDto {
    @Schema(description = "токен доступа", example = "avbll2bcapih35balkjba.afqewio132n1r-1234")
    String token;
}
