package com.example.ticketsForEvents.ticketsForEvents.DTO.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для запроса на авторизацию пользователя")
public record LoginUserRequestDto(
        @NotBlank(message = "{validation.error.user.email.is_empty}")
                @Email(message = "{validation.error.email.invalid}")
                @Schema(description = "Email пользователя",example = "email@mail.ru")
        String email,
        @NotBlank(message = "{validation.error.user.password.invalid")
                @Size(min = 6,max = 30,message = "{validation.error.user.password.size.invalid}")
                @Schema(description = "Пароль пользователя",example = "pass12345")
        String password
) {
}
