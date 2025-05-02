package com.example.ticketsForEvents.ticketsForEvents.Controllers.Advice;

import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchEventException;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchTicketException;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchEventException(NoSuchEventException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Event not found: " + ex.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("User not found: " + ex.getMessage());
    }
    @ExceptionHandler
    public ResponseEntity<String> handleNoSuchTicketException(NoSuchTicketException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ticket not found: " + ex.getMessage());
    }
}
