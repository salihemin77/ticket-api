package com.example.ticket_api.exception;

public class ClosedTicketException extends RuntimeException {
    public ClosedTicketException(String message) {
        super(message);
    }
}
