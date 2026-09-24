package com.example.ticket_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ClosedTicketException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleClosedTicket(ClosedTicketException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(InvalidStatusTransitionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidStatusTransitionException(InvalidStatusTransitionException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(TicketNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTicketNotFoundException(TicketNotFoundException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMessageNotFoundException(MessageNotFoundException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnauthorizedAccessException(UnauthorizedAccessException exception) {
        return exception.getMessage();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getFieldError()
                .getDefaultMessage();
    }



}