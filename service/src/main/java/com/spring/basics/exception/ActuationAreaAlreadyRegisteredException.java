package com.spring.basics.exception;

public class ActuationAreaAlreadyRegisteredException extends RuntimeException{

    public ActuationAreaAlreadyRegisteredException() {
        super("An actuation area with this name is already registered.");
    }
    public ActuationAreaAlreadyRegisteredException(String message) {
        super(message);
    }
}
