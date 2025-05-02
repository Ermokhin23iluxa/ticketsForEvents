package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.DTO.event.EventDto;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchEventException;
import com.example.ticketsForEvents.ticketsForEvents.Mapping.mapperEvent;
import com.example.ticketsForEvents.ticketsForEvents.Models.Event;
import com.example.ticketsForEvents.ticketsForEvents.Models.User;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.EventRepository;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final mapperEvent eventMapper;
    private final UserRepository userRepository;

    public EventDto createEventForUser(Long userId,EventDto eventDto){

        log.info("Attempting to create event for user with id: {}",userId);

        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+ userId));
        Event event = eventMapper.toEvent(eventDto);
        event.setDateOfEvent(LocalDateTime.now());
        event.setUser(user);
        event.setCountTicket(eventDto.getCountTicket());

        log.info("Creating event: {} for user with id: {}",eventDto.getName(),userId);

        Event savedEvent = eventRepository.save(event);

        log.info("Event successfully with id: {}",savedEvent.getID());

        return eventDto;
    }

    public List<EventDto> listByNameEvents(String name){
        List<Event>events;

        if(name!=null){
            events = eventRepository.findByName(name);
        }else{
            events = eventRepository.findAll();
        }
        return events.stream()
                .map(eventMapper::toEventDto)//маппим событие в dto
                .collect(Collectors.toList());
    }

    public List<EventDto> getListEvents() {
        List<Event>events = eventRepository.findAll();
        return events.stream()
                .map(eventMapper::toEventDto)//
                .collect(Collectors.toList());
    }

    public List<EventDto> getListEventsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new EntityNotFoundException("User not found with id:"+ userId));

        List<Event>events = eventRepository.findEventByUserID(userId);
        if(events.isEmpty()){
            throw new NoSuchEventException("No events found for user with id: " + userId);
        }
        return events.stream()
                .map(eventMapper::toEventDto)
                .collect(Collectors.toList());
    }

//    public EventDto createEvent(EventDto eventdto) throws IOException {
//
//        Event event = eventMapper.toEvent(eventdto);// замаппили dto в событие
//        log.info("Saving new Event. Name: {}; Location: {} ; Description: {}", event.getName(),event.getLocation(),event.getDescription());
//
//        Event createdEvent = eventRepository.save(event);
//        return eventMapper.toEventDto(createdEvent);//возвращаем замапленное событие
//
//    }

    public void deleteEvent(Long id){
        Event event = eventRepository.findById(id)
                        .orElseThrow(()->new NoSuchEventException("Event with id: "+ id + "does not exist!"));
        eventRepository.deleteById(id);
    }

    public EventDto getEventById(Long id){
        return eventMapper.toEventDto(
                eventRepository.findById(id).orElseThrow(()->new NoSuchEventException("Event with id " + id + "does not exist"))
        );
    }

    public Event updateEvent(Long id,Event updatedEvent){
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Event not found with id: "+id));
        existingEvent.setName(updatedEvent.getName());
        existingEvent.setDateOfEvent(updatedEvent.getDateOfEvent());
        existingEvent.setLocation(updatedEvent.getLocation());
        return eventRepository.save(existingEvent);
    }

}
