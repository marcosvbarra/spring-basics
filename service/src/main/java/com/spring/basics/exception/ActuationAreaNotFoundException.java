package com.spring.basics.exception;

public class ActuationAreaNotFoundException extends RuntimeException{

    public ActuationAreaNotFoundException() {
        super("Actuation area was not found.");
    }
    public ActuationAreaNotFoundException(String message) {
        super(message);
    }
}
