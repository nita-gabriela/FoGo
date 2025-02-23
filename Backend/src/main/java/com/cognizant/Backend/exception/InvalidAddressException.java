package com.cognizant.Backend.exception;

public class InvalidAddressException extends RuntimeException {
    public InvalidAddressException(String message) {
        super(message);
    }
}
