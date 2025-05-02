package com.example.ticketsForEvents.ticketsForEvents.Exeption;

public class NoSuchTicketException extends RuntimeException{
    public NoSuchTicketException(){
        super("No SUCH TICKET exist!");
    }
    public NoSuchTicketException(String message){
        super(message);
    }
}
