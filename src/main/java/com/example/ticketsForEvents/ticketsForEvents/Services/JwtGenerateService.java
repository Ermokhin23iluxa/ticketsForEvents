package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import org.springframework.stereotype.Service;

@Service
// содержит методы для генерации токена
public interface JwtGenerateService {
    String generateAccessToken(String userEmail, Role role);// генерируем access токен для доступа к защ.рес-м
    String generateRefreshToken(String userEmail,Role role);// обновленный токен после того как протух access токен
}
