package com.example.ticketsForEvents.ticketsForEvents.Exeption;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
    public UserAlreadyExistsException(){
        super("error.409.user.already_exists");
    }
}
