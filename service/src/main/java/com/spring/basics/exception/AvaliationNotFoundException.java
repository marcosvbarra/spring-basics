package com.spring.basics.exception;

public class AvaliationNotFoundException extends RuntimeException{

    public AvaliationNotFoundException() {
        super("Avaliation not found.");
    }
}
