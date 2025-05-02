package com.example.ticketsForEvents.ticketsForEvents.Mapping;

import com.example.ticketsForEvents.ticketsForEvents.DTO.ticket.TicketDto;
import com.example.ticketsForEvents.ticketsForEvents.Models.Ticket;
import org.springframework.stereotype.Component;


@Component
//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class TicketMapper {
    public TicketDto toTicketDto(Ticket ticket){
        return TicketDto
                .builder()
                .ID(ticket.getID())
                .ticketNumber(ticket.getTicketNumber())
                .price(ticket.getPrice())
                .seatNumber(ticket.getSeatNumber())
                .userId(ticket.getUser().getID())
                .eventId(ticket.getEvent().getID())
                .qrCode(ticket.getQrCode())
                .build();
    }
    public Ticket toTicket(TicketDto ticketDto){
        Ticket ticket  = new Ticket();
        ticket.setID(ticketDto.getID());
        ticket.setTicketNumber(ticketDto.getTicketNumber());
        ticket.setPrice(ticketDto.getPrice());
        ticket.setSeatNumber(ticketDto.getSeatNumber());
        return ticket;
    }
}
