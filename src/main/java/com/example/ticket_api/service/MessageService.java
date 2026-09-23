package com.example.ticket_api.service;

import com.example.ticket_api.entity.Message;

import java.util.List;

public interface MessageService  {
    Message save(Message message);

    Message findById(Integer id);

    List<Message> findAll();

    void deleteById(Integer id);

    Message update(Message message);
}
