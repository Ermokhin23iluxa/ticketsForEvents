package com.example.ticketsForEvents.ticketsForEvents.Controllers;

import com.example.ticketsForEvents.ticketsForEvents.DTO.user.UserDto;
import com.example.ticketsForEvents.ticketsForEvents.Services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity <List<UserDto>> getAllUsers(){
        log.info("Received request get all users");
        List <UserDto> users = userService.getAllUsers();
        log.info("Returning {} users",users.size());
        return ResponseEntity.ok(users);
    }
//    @GetMapping("/{id}")
//    public ResponseEntity<UserDto> getUserById(@PathVariable Long id){
//        return ResponseEntity.ok(userService.getUserById(id));
//    }
//    @PostMapping("/create")
//    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
//        UserDto createdUser = authService.createUser(userDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
//    }

//    @DeleteMapping("/{id}/delete")
//    public ResponseEntity <String> deleteUserById(@PathVariable Long id){
//        userService.deleteUser(id);
//        /*
//        Map<String,String> response = new HashMap<>();
//        response.put("message","User with id: " + id + "was successfully deleted!");
//        */
//        String message = "User with id: " + id + "was successfully deleted!";
//        return ResponseEntity.ok(message);
//    }

}
