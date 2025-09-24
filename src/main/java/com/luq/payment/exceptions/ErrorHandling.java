package com.luq.payment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)
public class ErrorHandling {
    Map<String, String> errors = new HashMap<>();

    @ExceptionHandler(NegativeAmountException.class)
    public ResponseEntity<Map<String, String>> exceptionFound(NegativeAmountException e) {
        errors.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(NegativeQuantityException.class)
    public ResponseEntity<Map<String, String>> exceptionFound(NegativeQuantityException e) {
        errors.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(InvalidJsonException.class)
    public ResponseEntity<Map<String, String>> exceptionFound(InvalidJsonException e) {
        errors.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(StripeErrorException.class)
    public ResponseEntity<Map<String, String>> exceptionFound(StripeErrorException e) {
        errors.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(errors);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> exceptionFound(NotFoundException e) {
        errors.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
    }
}
