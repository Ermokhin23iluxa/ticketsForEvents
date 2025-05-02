package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.DTO.auth.*;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.UserUnauthenticationException;
import com.example.ticketsForEvents.ticketsForEvents.Mapping.UserMapper;
import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import com.example.ticketsForEvents.ticketsForEvents.Models.User;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.UserRepository;
import com.example.ticketsForEvents.ticketsForEvents.Validation.JwtValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final JwtExtractorService jwtExtractorService;
    private final PasswordEncoder passwordEncoder;
    private final JwtStorageService jwtStorageService;
    private final JwtValidator jwtValidator;
    private final AuthenticationManager authenticationManager;


    public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto){
        log.info("Register new user with email: {}",registerUserRequestDto.email());

        userService.checkUserExists(registerUserRequestDto.email());

        User user = createUser(registerUserRequestDto);

        log.info("User registered successfully with email: {}",registerUserRequestDto.email());
        return userMapper.toRegisterUserResponseDto(userRepository.save(user));
    }

    public LoginUserResponseDto loginUser(LoginUserRequestDto loginUserRequestDto){
        log.info("User login with email: {}",loginUserRequestDto.email());

        try{
            Authentication authentication = authenticationUser(loginUserRequestDto);
            User user = (User) authentication.getPrincipal();// что это

            String accessToken = jwtStorageService.generateAccessToken(user.getEmail(),user.getRole());
            String refreshToken = jwtStorageService.generateRefreshToken(user.getEmail(),user.getRole());

            log.info("User login successfully with email: {}",user.getEmail());
            return new LoginUserResponseDto(accessToken,refreshToken); // почему и как работает
        }catch (AuthenticationException exception){
            log.error("Authentication failed user with emai: {}",loginUserRequestDto.email());
            throw new UserUnauthenticationException("error.401.user.unauthenticated");
        }
    }

    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto refreshTokenRequestDto){
        log.info("Refreshing token");
        String refreshToken = refreshTokenRequestDto.refreshToken();// что это
        jwtValidator.validateRefreshToken(refreshToken);// что это
        String userEmail = jwtExtractorService.extractUserEmail(refreshToken);// что это
        log.info("Extracted user email: {}",userEmail);
        Role role = jwtExtractorService.extractRole(refreshToken);// зачем
        String accessToken = jwtStorageService.generateAccessToken(userEmail,role); // зачем
        log.info("Token refresh successfully for user: {}",userEmail);
        return RefreshTokenResponseDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(){
        String userEmail = userService.getCurrentUserEmail();
        log.info("User with email {} successfully logout",userEmail);

        jwtStorageService.deleteTokenByUserEmail(userEmail);// починить и зачем это
    }

    private Authentication authenticationUser(LoginUserRequestDto loginUserRequestDto){
        log.info("Authenticate user with email: {}",loginUserRequestDto.email());
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserRequestDto.email(),
                        loginUserRequestDto.password()
                )
        );
    }

    private User createUser(RegisterUserRequestDto registerUserRequestDto){
        log.debug("Creating new user with email: {}",registerUserRequestDto.email());
        User user = userMapper.toUser(registerUserRequestDto);
        user.setPassword(passwordEncoder.encode(registerUserRequestDto.password()));
        user.setRole(Role.USER);
        return user;
    }

}
