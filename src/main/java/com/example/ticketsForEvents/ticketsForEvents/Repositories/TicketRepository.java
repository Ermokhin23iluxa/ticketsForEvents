package com.example.ticketsForEvents.ticketsForEvents.Repositories;

import com.example.ticketsForEvents.ticketsForEvents.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket> findByUserID(Long userId);// билеты по юзеру

    List<Ticket> findTicketsByUserID(Long userID);
    //List<Ticket> findBySeatID(Long seatID);// билеты по месту
}
