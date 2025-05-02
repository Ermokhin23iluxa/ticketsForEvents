package com.example.ticketsForEvents.ticketsForEvents.Exeption;

public class NoSuchUserException extends RuntimeException
{
    public NoSuchUserException(){
        super("No SUCH USER exist!");
    }
    public NoSuchUserException(String message){
        super(message);
    }
}
