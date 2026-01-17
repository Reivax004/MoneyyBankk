package com.example.exceptions;

public class LoanMessagingFailureException extends RuntimeException {
    public LoanMessagingFailureException(String message) {
        super(message);
    }
}
