package com.example.ticket_api.repository;

import com.example.ticket_api.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {

    List<Message> findByTicketUserId(Integer userId);
}
