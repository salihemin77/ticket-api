package com.example.ticket_api.controller;

import com.example.ticket_api.entity.Message;
import com.example.ticket_api.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }
    @GetMapping("/messages")
    public List<Message> findAll() {
        return messageService.findAll();
    }
    @GetMapping("/messages/{id}")
    public Message findById(@PathVariable Integer id) {
        return messageService.findById(id);
    }
    @PostMapping("/messages")
    public Message save(@RequestBody Message message) {
        return messageService.save(message);

    }
    @PutMapping("/messages/{id}")
    public Message update(@PathVariable Integer id, @RequestBody Message message) {
        message.setId(id);
        return messageService.update(message);

    }
    @DeleteMapping("/messages/{id}")
    public void delete(@PathVariable Integer id) {
        messageService.deleteById(id);
    }


    @GetMapping("/messages/my")
    public List<Message> findMyMessages() {
        return messageService.findMyMessages();
    }


}
