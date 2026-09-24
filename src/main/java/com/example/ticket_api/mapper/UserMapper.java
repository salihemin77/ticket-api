package com.example.ticket_api.mapper;


import com.example.ticket_api.dto.UserResponseDTO;
import com.example.ticket_api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()



        );
    }
}
