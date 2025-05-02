package com.example.ticketsForEvents.ticketsForEvents.Repositories;

import com.example.ticketsForEvents.ticketsForEvents.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserByName(String name);
    Boolean existsUserByName(String name);
    Boolean existsUserByEmail(String email);
}
