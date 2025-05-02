package com.example.ticketsForEvents.ticketsForEvents.Exeption;

public class NoSuchEventException extends RuntimeException{
    public NoSuchEventException(){
        super("No such event exist.");
    }
    public NoSuchEventException(String message){
        super(message);
    }
}
