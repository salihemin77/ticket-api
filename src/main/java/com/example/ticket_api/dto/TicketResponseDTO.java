package com.example.ticket_api.dto;

import com.example.ticket_api.enums.TicketStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

public class TicketResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private TicketStatus status;
    private LocalDateTime createdAt;

    public TicketResponseDTO(
            int id,
            String title,
            String description,
            TicketStatus status,
            LocalDateTime createdAt) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}