package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import org.springframework.stereotype.Service;

@Service
//в этом интерфейсе методы для извлечения информации из токена
public interface JwtExtractorService {// что это
    String extractUserEmail(String token);// возвращает email пользователя которому принадлежит токен
    Role extractRole(String token); // возвращает роль из токена
}
