package com.example.ticketsForEvents.ticketsForEvents.Repositories;

import com.example.ticketsForEvents.ticketsForEvents.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findByName(String name);

    List<Event> findEventByUserID(Long userId);
}
