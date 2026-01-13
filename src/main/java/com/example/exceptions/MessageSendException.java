package com.example.exceptions;

public class MessageSendException extends RuntimeException {
    public MessageSendException(String message) {
        super(message);
    }
}
