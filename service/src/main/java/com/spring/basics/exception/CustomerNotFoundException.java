package com.spring.basics.exception;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException() {
        super("Customer not found.");
    }
}
