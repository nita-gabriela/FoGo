package com.cognizant.Backend.exception;

public class InvalidCoordinatesException extends RuntimeException {
    public InvalidCoordinatesException(String message) {
        super(message);
    }
}