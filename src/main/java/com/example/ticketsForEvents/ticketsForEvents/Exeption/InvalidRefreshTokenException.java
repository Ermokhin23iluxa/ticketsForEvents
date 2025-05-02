package com.example.ticketsForEvents.ticketsForEvents.Exeption;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}
