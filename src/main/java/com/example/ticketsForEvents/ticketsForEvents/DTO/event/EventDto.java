package com.example.ticketsForEvents.ticketsForEvents.DTO.event;

import lombok.Builder;
import lombok.Data;

@Data// для геттеров и сеттеров полей из ломбок
@Builder
public class EventDto { // у дто свои поля которые могут понадобиться сервису или контроллеру
    private Long ID;
    private String name;
    private String location;
    private String description;
    private Integer countTicket;
}
