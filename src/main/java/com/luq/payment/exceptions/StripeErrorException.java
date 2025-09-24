package com.luq.payment.exceptions;

public class StripeErrorException extends RuntimeException {
    public StripeErrorException(String message) {
        super(message);
    }
}
