package com.spring.basics.exception;

public class CepNotFoundException extends RuntimeException{

    public CepNotFoundException() {
        super("Cep was not found.");
    }
    public CepNotFoundException(String message) {
        super(message);
    }
}
