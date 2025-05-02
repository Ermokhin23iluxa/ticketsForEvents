package com.example.ticketsForEvents.ticketsForEvents.Controllers;

import com.example.ticketsForEvents.ticketsForEvents.DTO.event.EventDto;
import com.example.ticketsForEvents.ticketsForEvents.Services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;
//    @GetMapping
//    public ResponseEntity<List<EventDto>> listEvents(@RequestParam(value = "name",required = false)String name){
//        List<Event> events = eventService.listEvents(name);
//        List<EventDto>eventDtos = events.stream()
//                .map(mapEvent::toEventDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(eventDtos);
//    }
    @GetMapping
    public ResponseEntity<List<EventDto>> getListEvents(){
        List<EventDto>eventsDto = eventService.getListEvents();
        return ResponseEntity.ok(eventsDto);
    }


    @GetMapping("/{userId}/userEvents")
    public ResponseEntity<List<EventDto>> getListEventsForUser(@PathVariable Long userId){
        List<EventDto> eventsDtoForUser = eventService.getListEventsForUser(userId);
        return ResponseEntity.ok(eventsDtoForUser);
    }
//    @GetMapping("/new")
//    public String showCreateEventForm(Model model){
//        model.addAttribute("eventDto",new EventDto());
//        return "events/new";
//    }

    @PostMapping("/{userId}/createEvent")
    public ResponseEntity<EventDto> createEventForUser(@PathVariable Long userId,@RequestBody EventDto eventDto){
        EventDto createdEvent = eventService.createEventForUser(userId,eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }


    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id){
        return ResponseEntity.ok(eventService.getEventById(id));
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteEventByid(@PathVariable Long id){
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/createAllEvent")
//    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) throws IOException {//зачем RequestBody
//        EventDto createdEventDto = eventService.createEvent(eventDto); // приняли в серсвис dto(из http) чтобы создать событие и там его размапили в event
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdEventDto);// что значит
//    }
    //
    //@RequestBody - чтобы прочитать данные из HTTP запрос в формате JSON и дессиреализовать в EventDto
    //ResponseEntity - тип ответа, который позволяет вернуть HTTP-status и тело ответа в одном обьекте
    // HttpStatus.CREATED - вернется 201, body(createdEventDto) - возвращает тело с объектом createdEventDto
}
