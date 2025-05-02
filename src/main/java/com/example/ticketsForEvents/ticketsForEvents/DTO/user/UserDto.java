package com.example.ticketsForEvents.ticketsForEvents.DTO.user;


import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO для пользователя")
public record UserDto(
        @Schema(description = "Уникальный id пользователя",example = "1")
        Long ID,
        @Schema(description = "Mail пользователя",example = "email@mail.ru")
        String email,
        @Schema(description = "Имя пользователя",example = "Илья Ильич")
        String name,
        @Schema(description = "Роль пользователя",example = "USER",allowableValues = {"USER","ADMIN"})
        Role role
) { }
