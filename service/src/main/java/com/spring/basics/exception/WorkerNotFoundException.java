package com.spring.basics.exception;

public class WorkerNotFoundException extends RuntimeException{

    public WorkerNotFoundException() {
        super("Worker not found.");
    }
}
