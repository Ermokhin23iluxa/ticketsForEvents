package com.example.ticketsForEvents.ticketsForEvents.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long ID;

    @Column(name="ticketNumber" ,length=10000)
    private String ticketNumber;

    @Column(name="purchaseDate")
    private LocalDateTime purchaseDate;

    @Column(name="price",nullable = false)
    @NotBlank(message = "Price is mandatory field")
    private BigDecimal price;

    @Column(name="qrCode")
    private String qrCode;

    @Column(name="seatNumber",length = 10000)
    private String seatNumber;


    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="event_id")
    private Event event;

}
