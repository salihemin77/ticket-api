package com.example.ticket_api.dto;

import java.time.LocalDateTime;

public class MessageResponseDTO  {
    private int id;
    private String content;
    private LocalDateTime createdAt;

    public MessageResponseDTO(int id, String content, LocalDateTime createdAt) {
      this.id = id;
      this.content = content;
      this.createdAt = createdAt;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
