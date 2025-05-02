package com.example.ticketsForEvents.ticketsForEvents.Exeption;

public class UserUnauthenticationException extends RuntimeException {
    public UserUnauthenticationException(String message) {
        super(message);
    }
    public UserUnauthenticationException(){
        super("error.401.unauthenticated.user");
    }
}
