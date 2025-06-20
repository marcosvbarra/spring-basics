package com.spring.basics.exception;

public class WorkerAlreadyRegisteredException extends RuntimeException{

    public WorkerAlreadyRegisteredException() {
        super("A worker with this email is already registered.");
    }
    public WorkerAlreadyRegisteredException(String message) {
        super(message);
    }
}
