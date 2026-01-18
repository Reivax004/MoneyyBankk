package com.example.exceptions;

public class LoanMessagingFailureException extends RuntimeException {
    public LoanMessagingFailureException(String message, Throwable cause) {
        super(message, cause);
    }
}
