package com.example.ticketsForEvents.ticketsForEvents.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long ID;

    @Column(name = "name",nullable = false,length = 100)
    @Size(min=4, max=100, message = "Name of event must be between 4 and 100 character")
    @NotBlank(message = "Name of event is mandatory field")
    private String name;

    @Column(name = "location",nullable = false,length = 255)
    @Size(min=2, max=255, message = "Name of event must be between 2 and 255 character")
    @NotBlank(message = "Location is mandatory field")
    private String location;

    private LocalDateTime dateOfEvent;

    @Column(name = "description",columnDefinition = "text")
    private String description;

    @Column(name="countTicket")
    @Min(value = 1,message="The ticket count must be at least 1")
    @Max(value = 1000,message = "The ticket count must not exceed 1000")
    private Integer countTicket;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn //for in key
    private User user;
    //CascadeType.REFRESH - синхронизация с юзером, тоесть если евент изменили то информация в юзере тоже меняется
    //FetchType.LAZY - user подгрузится не сразу , а только при обращении к нему
    //JoinColumn - поле будет хранится как внешний ключ в таблице events JC(name="user_id")

    //@PrePersist// инверсия управления зависимостей
    //private void init(){
        //dateOfEvent = LocalDateTime.now();
    //}

    // CascadeType.PERSIST - связанные сущности сохраняются вместе с родительской
    // CascadeType.MERGE - при обновлении родительской сущности связанные тоже обнавляются
    // CascadeType.REMOVE - при удалении родителского, удалятся дочерние (onetoone - onetomany)
    // CascadeType.DETACH - при отсоединении родительской, связанные сущности также отсоединяются
    // CascadeType.ALL - включает все выше
    // FetchType.EAGER - для небольших свзяей, когда данные всегда нужны USER-PROFILE
    // FetchType.LAZY - для больших связей, сущностей, когда данные можно подгрузить потом (по необходимсоти)
}
