package com.spring.basics.exception;

public class JobRequestNotAvailableException extends RuntimeException{

    public JobRequestNotAvailableException() {
        super("This job already have the maximum of interested workers.");
    }
}
