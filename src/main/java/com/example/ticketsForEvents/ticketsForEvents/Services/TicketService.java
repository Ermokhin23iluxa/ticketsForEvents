package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.DTO.ticket.TicketDto;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchEventException;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchTicketException;
import com.example.ticketsForEvents.ticketsForEvents.Mapping.TicketMapper;
import com.example.ticketsForEvents.ticketsForEvents.Models.Event;
import com.example.ticketsForEvents.ticketsForEvents.Models.Ticket;
import com.example.ticketsForEvents.ticketsForEvents.Models.User;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.EventRepository;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.TicketRepository;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.UserRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;


    public List<TicketDto> getListTickets(){
        List<Ticket>tickets = ticketRepository.findAll();
        if (tickets.isEmpty()){
            throw new NoSuchTicketException("No tickets found in the system!");
        }
        return tickets.stream()
                .map(ticketMapper::toTicketDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public TicketDto createTicketForUser(Long userID, TicketDto ticketDto){
        User user = userRepository.findById(userID)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+ userID));
        Event event = eventRepository.findById(ticketDto.getEventId())
                .orElseThrow(()->new NoSuchEventException("Event not found with id: " + ticketDto.getEventId()));

        if(event.getCountTicket()<=0){ // проверели есть ли билеты
            throw new IllegalStateException("No tickets available for this event");
        }


        Ticket ticket = ticketMapper.toTicket(ticketDto);
        ticket.setPurchaseDate(LocalDateTime.now());
        ticket.setUser(user);
        ticket.setEvent(event);

        event.setCountTicket(event.getCountTicket()-1);// Уменьшаем билеты при покупке
        eventRepository.save(event);


        Ticket savedTicket = ticketRepository.save(ticket);

        String qrCode = generateQrCode(savedTicket.getID());
        System.out.println("qrCode: " + qrCode);
        savedTicket.setQrCode(qrCode);

        ticketRepository.save(savedTicket);

        return ticketMapper.toTicketDto(savedTicket);//ticketDto
    }

    public String generateQrCode(Long ticketID){
        Ticket ticket = findTicketById(ticketID);
        String qrData = "Ticket ID: " + ticket.getID() + ", Event: " + ticket.getEvent().getName();
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE,300,300);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix,"PNG", outputStream);
            byte[]qrImageBytes = outputStream.toByteArray();
            String result = Base64.getEncoder().encodeToString(qrImageBytes);
            if (result.length()>50){
                result = result.substring(50,100);
            }
            return result;
        }catch (WriterException | IOException e){
            throw new RuntimeException("Failed to generate QR code",e);
        }

    }

    private Ticket findTicketById(Long ticketID){
        return ticketRepository.findById(ticketID)
                .orElseThrow(()->new NoSuchTicketException("Ticket not found with id: "+ticketID));
    }

    public TicketDto getTicketById(Long ticketId){
        return ticketMapper.toTicketDto(
                ticketRepository.findById(ticketId).orElseThrow(()->new NoSuchTicketException("ticket not found")));
    }

    public void deleteTicket(Long id){
        Ticket ticket = findTicketById(id);
        Event event = ticket.getEvent();
        if(event!=null){
            event.setCountTicket(event.getCountTicket()+1);
            eventRepository.save(event);
        }

        ticketRepository.deleteById(id);
    }

    public List<TicketDto> getTicketsByUserId(Long userID) {
        User user = userRepository.findById(userID)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+ userID));
        List<Ticket> tickets = ticketRepository.findTicketsByUserID(userID);
        return tickets.stream()
                .map(ticketMapper::toTicketDto)
                .collect(Collectors.toList());
    }

}
