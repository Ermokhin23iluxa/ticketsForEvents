package com.example.ticketsForEvents.ticketsForEvents.Filter;

import com.example.ticketsForEvents.ticketsForEvents.Exeption.InvalidTokenException;
import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import com.example.ticketsForEvents.ticketsForEvents.Services.JwtExtractorService;
import com.example.ticketsForEvents.ticketsForEvents.Validation.JwtValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MessageSource messageSource;
    private final JwtExtractorService jwtExtractorService;
    private final JwtValidator jwtValidator;// проверяет является ли токен действительным


    // метод для каждого входящего http запроса
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);//извлекаем токен из запроса
        try{
            if(token!= null){
                jwtValidator.validateAccessToken(token);// подаем токен в валидатор и проверяем его

                String username = jwtExtractorService.extractUserEmail(token);//достаем мэйл из действующего токена
                log.info("Extracted username: {}",username);

                Role role = jwtExtractorService.extractRole((token));
                log.info("Extracted role: {}",role);

                // создание списка полномочий на основе ролей
                List<GrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(role.name()));

                // обьект Authentication содержит данные о пользователе
                Authentication auth = new UsernamePasswordAuthenticationToken(username,null,authorities);
                // устанавливаем объект в SecurityContextHolder что делает пользователя аутентифицированным для запроса
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("User authenticated: {}",username);
            }
            filterChain.doFilter(request,response);// если токен действ. то передаем
                                                    // его дальше ( к controller или фильтру)
        }catch (InvalidTokenException ex){
            log.error("Invalid token, send response unauthorized for user");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("text/plain; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(
                    messageSource.getMessage("error.403.user.unauthorized", null,"error.401.user.unauthorized", request.getLocale())
            );
        }

    }
    // метод проверяет наличие заголовка Authorization и извлекает токен убирая Bearer_
    private String extractToken(HttpServletRequest request){
        log.info("Extract token from request header");
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken!=null || bearerToken.startsWith("Bearer ")){
            log.info("token != null");
            return bearerToken.substring(7);// обрезаем Bearer и достаем чистый токен
        }
        log.info("token == null");
        return null;
    }
}
