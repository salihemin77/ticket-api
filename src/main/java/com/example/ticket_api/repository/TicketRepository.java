package com.example.ticket_api.repository;

import com.example.ticket_api.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByUserId(Integer userId);



}
