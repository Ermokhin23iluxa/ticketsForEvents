package com.example.ticketsForEvents.ticketsForEvents.Services;

import com.example.ticketsForEvents.ticketsForEvents.DTO.auth.RegisterUserRequestDto;
import com.example.ticketsForEvents.ticketsForEvents.DTO.user.UserDto;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.NoSuchUserException;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.UserAlreadyExistsException;
import com.example.ticketsForEvents.ticketsForEvents.Exeption.UserUnauthenticationException;
import com.example.ticketsForEvents.ticketsForEvents.Mapping.UserMapper;
import com.example.ticketsForEvents.ticketsForEvents.Models.User;
import com.example.ticketsForEvents.ticketsForEvents.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers(){
        log.info("Список всех юзеров");
        List <User> users = userRepository.findAll();// из базы данных доастали юзеров и положили в users
        if (users.isEmpty()){
            throw new NoSuchUserException("No users found in the system!");
        }
        return users.stream()//stream - преобразуем список в поток
                .map(userMapper::toUserDto)//map - операция которая выполняется для каждого элемента потока\преобразуем user->userDto
                .collect(Collectors.toList());//collect - объединяет все эл в коллекцию// собираем List<UserDto>
    }

//    public UserDto createUser(UserDto userDto){
//        User user = userMapper.toUser(userDto);
//        user.setDateOfCreated(LocalDateTime.now());
//        user.setActive(true);
//        user = userRepository.save(user);
//        return userMapper.toUserDto(user);//userDto
//    }

    public void checkUserExists(String email){
        log.info("Checking user exists with email: {}",email);
        if(userRepository.existsUserByEmail(email)){
            log.info("User with email {} already exists",email);
            throw new UserAlreadyExistsException("error.409.user.already_exists");
        }
        log.info("User with email {} no exist",email);
    }

    public String getCurrentUserEmail(){
        log.info("Get current user email");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// что это

        log.info("Check authentication");
        if(authentication.isAuthenticated()){
            log.info("User is authenticated");
            return authentication.getName();
        }
        log.error("User is unauthenticated");
        throw new UserUnauthenticationException("error.401.user.unauthenticated");

//        User user = userRepository.findByEmail(this.getCurrentUserEmail())
//                .orElseThrow(()->{
//                    log.error("User not found");
//                    return new UserUnauthenticationException("error.401.user.unauthenticated");
//                });
//        log.info("Email {} for current user",user.getEmail());
//        return user.getEmail();
    }

    public User getCurrentUser(){
        log.info("Getting current user");
        User user = userRepository.findByEmail(this.getCurrentUserEmail())
                .orElseThrow(()->{
                            log.error("User not found");
                            return new UserUnauthenticationException("error.401.user.unauthenticated");
                        });
        log.info("Current user get successfully. User id: {}",user.getID());
        return user;
    }

    public UserDto getUserById(Long id){
        if(id==null || id<=0){
            throw new IllegalArgumentException("User ID must be a positive number!");
        }
        return userMapper.toUserDto(
                userRepository.findById(id).orElseThrow(()->new NoSuchUserException("User with id: "+ id +" does not exist!")));
    }

    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new NoSuchUserException("Cannot delete: User with id: " + id + " does not exist!");
        }
        userRepository.deleteById(id);

    }

}
