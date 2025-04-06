package com.alexander.exception.exceptions;

public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
