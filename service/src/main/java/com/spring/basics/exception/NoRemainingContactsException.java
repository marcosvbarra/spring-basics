package com.spring.basics.exception;

public class NoRemainingContactsException extends RuntimeException{

    public NoRemainingContactsException() {
        super("The user does not have remaining contacts.");
    }
}
