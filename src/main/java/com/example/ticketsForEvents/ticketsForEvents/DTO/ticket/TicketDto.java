package com.example.ticketsForEvents.ticketsForEvents.DTO.ticket;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TicketDto {
    private Long ID;
    private String ticketNumber;
    private BigDecimal price;
    private String qrCode;
    private String seatNumber;
    private Long userId; // ID пользователя
    private Long eventId;
}
