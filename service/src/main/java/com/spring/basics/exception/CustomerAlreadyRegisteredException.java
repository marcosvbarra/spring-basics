package com.spring.basics.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomerAlreadyRegisteredException extends RuntimeException{

    public CustomerAlreadyRegisteredException() {
        super("A customer with this email is already registered.");
    }
}
