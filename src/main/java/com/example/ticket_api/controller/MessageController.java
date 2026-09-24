package com.example.ticket_api.controller;

import com.example.ticket_api.dto.MessageResponseDTO;
import com.example.ticket_api.entity.Message;
import com.example.ticket_api.mapper.MessageMapper;
import com.example.ticket_api.mapper.UserMapper;
import com.example.ticket_api.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private MessageService messageService;
    private MessageMapper messageMapper;

    public MessageController(MessageMapper messageMapper, MessageService messageService) {
        this.messageMapper = messageMapper;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    public List<MessageResponseDTO> findAll() {
        return messageService.findAll().stream().map(messageMapper::toDTO).toList();
    }
    @GetMapping("/messages/{id}")
    public MessageResponseDTO findById(@PathVariable Integer id) {
        return messageMapper.toDTO(messageService.findById(id));
    }
    @PostMapping("/messages")
    public Message save(@Valid @RequestBody Message message) {
        return messageService.save(message);

    }
    @PutMapping("/messages/{id}")
    public Message update(@PathVariable Integer id,@Valid @RequestBody Message message) {
        message.setId(id);
        return messageService.update(message);

    }
    @DeleteMapping("/messages/{id}")
    public void delete(@PathVariable Integer id) {
        messageService.deleteById(id);
    }


    @GetMapping("/messages/my")
    public List<MessageResponseDTO> findMyMessages() {
        return messageService.findMyMessages().stream().map(messageMapper::toDTO).toList();
    }


}
