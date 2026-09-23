package com.example.ticket_api.entity;

import com.example.ticket_api.enums.TicketStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   @Column(name = "title")
    private String title;

   @Column(name = "description")
    private String description;
   @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;
   @Column(name = "createdAt")
    private LocalDateTime createdAt;

   @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

   public  Ticket() {

   }

    public Ticket(LocalDateTime createdAt, String description, TicketStatus status, String title) {
        this.createdAt = createdAt;
        this.description = description;
        this.status = status;
        this.title = title;

    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "cretedAt=" + createdAt +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", user=" + user +
                '}';
    }
}
