package com.example.ticketsForEvents.ticketsForEvents.Models;

import com.example.ticketsForEvents.ticketsForEvents.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="app_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "password")
        })
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long ID;

    @Column(name = "email",unique = true,nullable = false)
    @Size(min=4, max=30, message = "Name must be between 4 and 30 character")
    @NotBlank(message = "Email is mandatory field")
    private String email;

    @Column(name = "password",length = 100,unique = true,nullable = false)
    @Size(min=4, max=32, message = "Password must be between 4 and 32 character")
    @NotBlank(message = "Password is mandatory field")
    private String password;

    @Column(name = "name",nullable = false)
    @Size(min=4, max=30, message = "Name must be between 4 and 30 character")
    @NotBlank(message = "Name is mandatory field")
    private String name;

    @Column(name = "phoneNumber",nullable = false)
    @Pattern(regexp = "\\d{11}",message = "Mobile number must have exactly 11 digits")
    private String phoneNumber;

    @Column(name = "active")
    private boolean active;

    @Column(name = "role",nullable = false)
    private Role role;

    private LocalDateTime dateOfCreated;


    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER, mappedBy = "user")
    private List<Event> events = new ArrayList<>();

    // event - поле user
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "user")
    private List<Ticket> tickets = new ArrayList<>();


    // методы с класс UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {// обьяснить
        return List.of(new SimpleGrantedAuthority((role.name())));// обьяснить
    }// методы возвращает коллекцию объектов GrantedAuthority, которые представляют права доступа пользователя
    // Это могут быть роли или конкретные разрешения, которые определяют, что пользователь может делать в системе.

    @Override
    public String getUsername() {
        return email;
    }
}
