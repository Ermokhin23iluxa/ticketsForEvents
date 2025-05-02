package com.example.ticketsForEvents.ticketsForEvents.DTO.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для регистрации нового пользователя")
public record RegisterUserRequestDto(
        @NotBlank(message = "{validation.error.user.name.is_empty}")
                @Size(min = 4,max=40, message = "{validation.error.user.name.size.invalid}")
                @Schema(description = "Имя пользователя", example = "Иван Иванов", minLength = 4,maxLength = 40)
        String name,
        @NotBlank(message = "{validation.error.email.is_empty}")
                @Email(message="{validation.error.email.invalid}")
                @Size(min=3,max=255,message = "{validation.error.email.size.invalid}")
                @Schema(description = "Email пользователя", example = "irit@mail.ru",minLength = 3,maxLength = 255)
        String email,
        @NotBlank(message = "{validation.error.password.is_empty}")
                @Size(min = 6,max = 30,message = "{validation.error.password.size.invalid}")
                @Schema(description = "Пароль пользователя",example = "pas12345",minLength = 6,maxLength = 30)
        String password
)
{ }
