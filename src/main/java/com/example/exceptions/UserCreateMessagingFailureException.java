package com.example.exceptions;

public class UserCreateMessagingFailureException extends RuntimeException {
    public UserCreateMessagingFailureException(String message) {
        super(message);
    }
}
