package com.example.ticket_api.service;

import com.example.ticket_api.entity.Message;
import com.example.ticket_api.entity.Ticket;
import com.example.ticket_api.enums.TicketStatus;
import com.example.ticket_api.exception.ClosedTicketException;
import com.example.ticket_api.exception.MessageNotFoundException;
import com.example.ticket_api.exception.TicketNotFoundException;
import com.example.ticket_api.repository.MessageRepository;
import com.example.ticket_api.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private MessageRepository messageRepository;
    private TicketRepository ticketRepository;

    public MessageServiceImpl(MessageRepository messageRepository,
                              TicketRepository ticketRepository) {
        this.messageRepository = messageRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Message save(Message message) {

        Ticket ticket = ticketRepository.findById(message.getTicket().getId())
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found"));

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new ClosedTicketException("Closed ticket cannot receive message");
        }

        message.setTicket(ticket);

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

        if (ticket.getStatus() == TicketStatus.CLOSED) {
            throw new RuntimeException("Closed ticket message cannot be updated");
        }

        existingMessage.setContent(message.getContent());
        existingMessage.setTicket(ticket);

        return messageRepository.save(existingMessage);
    }
}