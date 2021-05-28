package com.spring.basics.exception;

public class JobRequestNotFoundException extends RuntimeException{

    public JobRequestNotFoundException() {
        super("Job Request not found.");
    }
}
