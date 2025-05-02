package com.example.ticketsForEvents.ticketsForEvents.Validation;

import com.example.ticketsForEvents.ticketsForEvents.Exeption.InvalidRefreshTokenException;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.InvalidTokenException;
import com.example.ticketsForEvents.ticketsForEvents.Services.JwtExtractorService;
import com.example.ticketsForEvents.ticketsForEvents.Services.JwtStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtValidator {
    private final JwtStorageService jwtStorageService;// чтобы получить токен из хранилища
    // сравнивает токен из запроса с ожидаемым токеном(токеном в системе)

    private final JwtExtractorService jwtExtractorService;// чтобы извлечь данные из токена мэйл-роль

    // проверка переданного access токена является ли он действительным
    public void validateAccessToken(String token){
        log.info("Validating access token");
        String userEmail = jwtExtractorService.extractUserEmail(token);// извлекли мэйл
        log.info("Extract userEmail from access token: {}",userEmail);

        String expectedAccessToken = jwtStorageService.getAccessTokenByUserEmail(userEmail);// получаем токен,
                                                                            // который система считает валидным

        if(Objects.isNull(expectedAccessToken) || !expectedAccessToken.equals(token)){
            log.error("Access token validation failed for: {}",userEmail);
            throw new InvalidTokenException();
        }
        log.info("Access token validation successful for: {}",userEmail);
    }
    // тоже самое только для refresh токена
    public void validateRefreshToken(String token){
        log.info("Validating refresh token");

        String userEmail = jwtExtractorService.extractUserEmail(token);
        log.info("Extract userEmail from refresh token: {}", userEmail);

        String expectedRefreshToken = jwtStorageService.getRefreshTokenByUserEmail(userEmail);

        if(Objects.isNull(expectedRefreshToken)|| !expectedRefreshToken.equals(token)){
            log.error("Refresh token validation failed for: {}",userEmail);
            throw new InvalidRefreshTokenException("error.409.refresh_token.invalid");
        }
        log.info("Access refresh token validation successful for: {}",userEmail);
    }


}
