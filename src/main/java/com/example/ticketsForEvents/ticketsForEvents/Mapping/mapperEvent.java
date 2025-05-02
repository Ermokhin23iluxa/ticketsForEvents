package com.example.ticketsForEvents.ticketsForEvents.Mapping;

import com.example.ticketsForEvents.ticketsForEvents.DTO.event.EventDto;
import com.example.ticketsForEvents.ticketsForEvents.Models.Event;
import org.springframework.stereotype.Component;


//@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
//Mapper и MapStruct автоматическая реализация интерфейсов которые занимаются маппингом
//componentModel = MappingConstants.ComponentModel.SPRING говорим спрингу что это бин и пусть сам его внедряет
@Component
public class mapperEvent {//interface
    public EventDto toEventDto(Event event){    //EventDto toEventDto(Event event);
        return EventDto
                .builder()
                .ID(event.getID())
                .name(event.getName())
                .location(event.getLocation())
                .description(event.getDescription())
                .countTicket(event.getCountTicket())
                .build();
    }

    public Event toEvent(EventDto eventDto){    //Event toEvent(EventDto eventDto);
        Event event = new Event();
        event.setID(eventDto.getID());
        event.setName(eventDto.getName());
        event.setLocation(eventDto.getLocation());
        event.setDescription(eventDto.getDescription());
        event.setCountTicket(eventDto.getCountTicket());
        return event;
    }
}
