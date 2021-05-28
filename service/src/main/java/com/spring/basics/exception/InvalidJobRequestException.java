package com.spring.basics.exception;

public class InvalidJobRequestException extends RuntimeException{

    public InvalidJobRequestException() {
        super("Invalid job request.");
    }
    public InvalidJobRequestException(String message) {
        super(message);
    }
}
