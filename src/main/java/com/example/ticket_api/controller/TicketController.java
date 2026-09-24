package com.example.ticket_api.controller;

import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public List<Ticket> findAll() {
        return ticketService.findAll();
    }
    @GetMapping("/tickets/{id}")
    public Ticket findById(@PathVariable Integer id) {
        return ticketService.findById(id);
    }
    @PostMapping("/tickets")
    public Ticket create(@RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }
    @DeleteMapping("/tickets/{id}")
    public void delete(@PathVariable Integer id) {
        ticketService.deleteById(id);
    }
    @PutMapping("/tickets/{id}")
    public Ticket update(@PathVariable Integer id, @RequestBody Ticket ticket) {
        ticket.setId(id);
        return ticketService.update(ticket);

    }
    @GetMapping("/tickets/my")
    public List<Ticket> findMyTickets(){
        return ticketService.findMyTickets();
    }





}
