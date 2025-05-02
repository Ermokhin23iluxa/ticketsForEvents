package com.example.ticketsForEvents.ticketsForEvents.DTO.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос регистрации пользователя с данными")
public record RegisterUserResponseDto (
        @Schema(description = "Идентификатор пользователя",example = "1")
        Long id,
        @Schema(description = "Имя пользователя",example = "Илья Мочалин")
        String name,
        @Schema(description = "Email пользователя", example = "irit@mail.ru")
        String email
){
}
