package com.alexander.exception;

import com.alexander.exception.exceptions.EmailAlreadyInUseException;
import com.alexander.exception.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<String> handleEmailAlreadyInUseException() {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already in use!");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("User not found!");
    }
}
