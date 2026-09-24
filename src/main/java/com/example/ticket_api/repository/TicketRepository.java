package com.example.ticket_api.repository;

import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.enums.TicketStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByUserId(Integer userId);
    List<Ticket> findByTitleContainingIgnoreCase(String title);
    List<Ticket> searchByTitle(String title);
    List<Ticket> findByStatus(TicketStatus status);





}
