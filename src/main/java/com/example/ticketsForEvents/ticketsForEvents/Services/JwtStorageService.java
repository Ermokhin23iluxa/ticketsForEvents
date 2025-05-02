package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import org.springframework.stereotype.Service;

@Service
public interface JwtStorageService {

    String generateAccessToken(String userEmail, Role role);

    String generateRefreshToken(String userEmail,Role role);

    String getAccessTokenByUserEmail(String userEmail);// возвращает токен для текущего  юзера
    // для провверки активного токена

    String getRefreshTokenByUserEmail(String userEmail);// возвращает рефреш токен для текущего email

    void deleteTokenByUserEmail(String userEmail);//удаление токена (пример выход из системы)
}
