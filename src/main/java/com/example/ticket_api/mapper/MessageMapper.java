package com.example.ticket_api.mapper;

import com.example.ticket_api.dto.MessageResponseDTO;
import com.example.ticket_api.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageResponseDTO toDTO(Message message) {
        return new MessageResponseDTO(
           message.getId(),
           message.getContent(),
           message.getCreateAt()


        );
    }
}
