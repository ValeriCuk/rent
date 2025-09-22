package org.example.rent.exceptions;

public class InvalidStatusException extends RuntimeException {
    public InvalidStatusException(String status) {
        super("Invalid status: " + status);
    }
}
