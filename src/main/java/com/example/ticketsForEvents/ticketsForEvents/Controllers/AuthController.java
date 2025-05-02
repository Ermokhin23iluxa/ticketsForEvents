package com.example.ticketsForEvents.ticketsForEvents.Controllers;

import com.example.ticketsForEvents.ticketsForEvents.DTO.auth.*;
import com.example.ticketsForEvents.ticketsForEvents.Services.AuthService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@RequestBody RegisterUserRequestDto requestDto){

        RegisterUserResponseDto responseDto = authService.registerUser(requestDto);
        log.info("Register user with email {} and id {} ",responseDto.email(),responseDto.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> loginUser(@RequestBody LoginUserRequestDto requestDto){
        LoginUserResponseDto responseDto = authService.loginUser(requestDto);
        log.info("Login user with email: {} ",requestDto.email());
        return ResponseEntity.ok(responseDto);
    }

//    public ResponseEntity<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto requestDto){
//
//    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(){ // что значит ResponseEntity<Void>
        log.info("Received request logout user");
        authService.logout();
        log.info("User logged out successfully");
        return ResponseEntity.noContent().build();// почему так
    }
}
