package com.example.ticket_api.mapper;

import com.example.ticket_api.dto.TicketResponseDTO;
import com.example.ticket_api.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {
    public TicketResponseDTO toDTO(Ticket ticket) {

        return new TicketResponseDTO(

                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getCreatedAt()

        );

    }


}
