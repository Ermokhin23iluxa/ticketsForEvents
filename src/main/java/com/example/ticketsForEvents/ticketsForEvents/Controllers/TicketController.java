package com.example.ticketsForEvents.ticketsForEvents.Controllers;

import com.example.ticketsForEvents.ticketsForEvents.DTO.ticket.TicketDto;
import com.example.ticketsForEvents.ticketsForEvents.Services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/{userID}/create")
    public ResponseEntity<TicketDto> createTicket(@PathVariable Long userID, @RequestBody TicketDto ticketDto) throws IOException {
        TicketDto createdTicketDto = ticketService.createTicketForUser(userID,ticketDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicketDto);
    }
    @GetMapping
    public ResponseEntity<List<TicketDto>> getAllTickets(){
        List<TicketDto> ticketDtos = ticketService.getListTickets();
        return ResponseEntity.ok(ticketDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteTicketById(@PathVariable Long id){
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{userID}/getTickets")
    public ResponseEntity<List<TicketDto>>getTicketsByUserId(@PathVariable Long userID){
        return ResponseEntity.ok(ticketService.getTicketsByUserId(userID));
    }

}
