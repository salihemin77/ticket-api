package com.example.ticket_api.controller;

import com.example.ticket_api.dto.TicketResponseDTO;
import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.enums.TicketStatus;
import com.example.ticket_api.mapper.TicketMapper;
import com.example.ticket_api.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TicketController {
    private TicketService ticketService;
    private TicketMapper ticketMapper;

    public TicketController(TicketMapper ticketMapper, TicketService ticketService) {
        this.ticketMapper = ticketMapper;
        this.ticketService = ticketService;
    }

    @GetMapping("/tickets")
    public List<TicketResponseDTO> findAll() {

        return ticketService.findAll().stream().map(ticketMapper::toDTO).toList();
    }
    @GetMapping("/tickets/{id}")
    public TicketResponseDTO findById(@PathVariable Integer id) {
        return ticketMapper.toDTO(ticketService.findById(id));
    }
    @PostMapping("/tickets")
    public Ticket create(@Valid @RequestBody Ticket ticket) {
        return ticketService.save(ticket);
    }
    @DeleteMapping("/tickets/{id}")
    public void delete(@PathVariable Integer id) {
        ticketService.deleteById(id);
    }
    @PutMapping("/tickets/{id}")
    public Ticket update( @PathVariable Integer id,@Valid @RequestBody Ticket ticket) {
        ticket.setId(id);
        return ticketService.update(ticket);

    }
    @GetMapping("/tickets/my")
    public List<TicketResponseDTO> findMyTickets(){
        return ticketService.findMyTickets().stream().map(ticketMapper::toDTO).toList();
    }

    @GetMapping("/tickets/search")
    public List<TicketResponseDTO> searchByTitle(@RequestParam String title) {
        return ticketService.searchByTitle(title).stream().map(ticketMapper::toDTO).toList();

    }

    @GetMapping("/tickets/status")
    public List<TicketResponseDTO> findByStatus(@RequestParam TicketStatus status) {
        return ticketService.findByStatus(status).stream().map(ticketMapper::toDTO).toList();
    }





}
