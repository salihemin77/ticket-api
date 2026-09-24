package com.example.ticket_api.service;

import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.entity.User;
import com.example.ticket_api.enums.Role;
import com.example.ticket_api.enums.TicketStatus;
import com.example.ticket_api.exception.InvalidStatusTransitionException;
import com.example.ticket_api.exception.TicketNotFoundException;
import com.example.ticket_api.exception.UnauthorizedAccessException;
import com.example.ticket_api.exception.UserNotFoundException;
import com.example.ticket_api.repository.TicketRepository;
import com.example.ticket_api.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    public TicketServiceImpl(TicketRepository ticketRepository,
                             UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Ticket save(Ticket ticket) {

        User user = userRepository.findById(ticket.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        ticket.setUser(user);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket findById(Integer id) {
      Ticket ticket=   ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
         String email = authentication.getName();
         User user=userRepository.findByEmail(email).orElseThrow(()->new UserNotFoundException("User not found"));


        if (user.getRole() == Role.CUSTOMER &&
                ticket.getUser().getId() != user.getId()) {

            throw new UnauthorizedAccessException("You can only view your own tickets");
        }


         return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket update(Ticket ticket) {

        // Önce DB'deki gerçek ticket'ı buluyoruz
        Ticket existingTicket = ticketRepository.findById(ticket.getId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        TicketStatus oldStatus = existingTicket.getStatus();
        TicketStatus newStatus = ticket.getStatus();

        // OPEN → sadece IN_PROGRESS olabilir
        if (oldStatus == TicketStatus.OPEN &&
                newStatus != TicketStatus.IN_PROGRESS) {

            throw new InvalidStatusTransitionException(
                    "Invalid status transition"
            );
        }

        // IN_PROGRESS → sadece RESOLVED olabilir
        if (oldStatus == TicketStatus.IN_PROGRESS &&
                newStatus != TicketStatus.RESOLVED) {

            throw new InvalidStatusTransitionException(
                    "Invalid status transition"
            );
        }

        // RESOLVED → sadece CLOSED olabilir
        if (oldStatus == TicketStatus.RESOLVED &&
                newStatus != TicketStatus.CLOSED) {

            throw new InvalidStatusTransitionException(
                    "Invalid status transition"
            );
        }

        // CLOSED ticket artık güncellenemez
        if (oldStatus == TicketStatus.CLOSED) {

            throw new InvalidStatusTransitionException(
                    "Closed ticket cannot be updated"
            );
        }

        // Sadece değişmesine izin verdiğimiz alanları güncelliyoruz
        existingTicket.setTitle(ticket.getTitle());
        existingTicket.setDescription(ticket.getDescription());
        existingTicket.setStatus(newStatus);

        // user ve createdAt'e dokunmuyoruz
        return ticketRepository.save(existingTicket);
    }

    @Override
    public List<Ticket> findMyTickets() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user= userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.getId();
        return ticketRepository.findByUserId(user.getId());
    }

    @Override
    public List<Ticket> searchByTitle(String title) {
        return ticketRepository.findByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status) ;
    }

    @Override
    public Page<Ticket> findAll(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }
}