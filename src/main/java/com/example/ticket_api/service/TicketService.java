package com.example.ticket_api.service;

import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.enums.TicketStatus;

import java.util.List;

public interface TicketService {
    Ticket save(Ticket ticket);
    Ticket findById(Integer id);
    List<Ticket> findAll();
    void deleteById(Integer id);
    Ticket update(Ticket ticket);
    List<Ticket> findMyTickets();
    List<Ticket> searchByTitle(String title);
    List<Ticket> findByStatus(TicketStatus status);

}
