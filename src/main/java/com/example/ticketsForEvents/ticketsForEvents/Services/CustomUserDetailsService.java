package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // конструктор для всех final
@Slf4j// логи
// этот класс мост между механизмом аутентификации ss и бд
public class CustomUserDetailsService implements UserDetailsService {
    // security использует UserDetailsService чтобы загрузить данные пользователя из бд для  аутентификации
    private final UserRepository userRepository;

    // задача метода во время аутентификации принять (email пользователя) найти в бд и вернуть
        // userDetails для дальнейшей аутентификации
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Loading user with email: {}",username);
        return userRepository.findByEmail(username)// ищем юзера по username
                .map(user->{// проходимся по каждому юзеру если нашли юзера выводим его email и возвращаем его
                    log.info("User found: {}",user.getEmail());
                    return user;
                })
                .orElseThrow(()->{
                    log.error("User not found with email: {}",username);
                    return new UsernameNotFoundException("User not found with email: "+ username);
                });

    }
}
