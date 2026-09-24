package com.example.ticket_api.service;

import com.example.ticket_api.entity.Message;
import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.entity.User;
import com.example.ticket_api.enums.Role;
import com.example.ticket_api.enums.TicketStatus;
import com.example.ticket_api.exception.*;
import com.example.ticket_api.repository.MessageRepository;
import com.example.ticket_api.repository.TicketRepository;
import com.example.ticket_api.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private TicketRepository ticketRepository;
    private UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, TicketRepository ticketRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Message save(Message message) {

        Ticket ticket = ticketRepository.findById(message.getTicket().getId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException("User not found"));
        if (user.getRole() == Role.CUSTOMER &&
                ticket.getUser().getId() != user.getId()) {

            throw new UnauthorizedAccessException("You can only send messages to your own tickets");
        }


        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new ClosedTicketException("Closed ticket cannot receive message");
        }

        message.setTicket(ticket);
        message.setUser(user);
        message.setCreateAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    @Override
    public Message findById(Integer id) {
        return messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
    }

    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Message update(Message message) {

        Message existingMessage = messageRepository.findById(message.getId())
                .orElseThrow(() -> new MessageNotFoundException("Message not found"));

        Ticket ticket = ticketRepository.findById(message.getTicket().getId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));

        if (user.getRole() == Role.CUSTOMER &&
                existingMessage.getUser().getId() != user.getId()) {

            throw new UnauthorizedAccessException(
                    "You can only update your own messages"
            );
        }



        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new ClosedTicketException("Closed ticket message cannot be updated");
        }

        existingMessage.setContent(message.getContent());
        existingMessage.setTicket(ticket);

        return messageRepository.save(existingMessage);
    }

    @Override
    public List<Message> findMyMessages() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user=userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
        return messageRepository.findByTicketUserId(user.getId());


    }
}