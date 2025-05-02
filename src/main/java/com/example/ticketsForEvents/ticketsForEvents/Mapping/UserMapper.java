package com.example.ticketsForEvents.ticketsForEvents.Mapping;

import com.example.ticketsForEvents.ticketsForEvents.DTO.auth.RegisterUserRequestDto;
import com.example.ticketsForEvents.ticketsForEvents.DTO.auth.RegisterUserResponseDto;
import com.example.ticketsForEvents.ticketsForEvents.DTO.user.UserDto;
import com.example.ticketsForEvents.ticketsForEvents.Models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    RegisterUserResponseDto toRegisterUserResponseDto(User user);
    @Mapping(target = "password",ignore = true)
    User toUser(RegisterUserRequestDto registerUserRequestDto);

    UserDto toUserDto(User user);


//    public UserDto toUserDto(User user){
//        return UserDto
//                .builder()
//                .ID(user.getID())
//                .email(user.getEmail())
//                .name(user.getName())
//                .password(user.getPassword())
//                .phoneNumber(user.getPhoneNumber())
//                .build();
//    }
//    public User toUser(UserDto userDto){
//        User user = new User();
//        user.setID(userDto.getID());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPhoneNumber(userDto.getPhoneNumber());
//        user.setPassword(userDto.getPassword());
//        return user;
//    }
}
