package com.spring.basics.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptionMethod(Exception ex) {

        ExceptionMessage exceptionMessageObj = buildExceptionMessage(ex, null);
        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidExceptionMethod(Exception ex) {


        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
        for (int i = 0; i < fieldErrors.size(); i++) {
            message.append(fieldErrors.get(i).getDefaultMessage());
            if (fieldErrors.size() > 1 && i < fieldErrors.size() - 1) {
                message.append("; ");
            }
        }

        ExceptionMessage exceptionMessageObj = buildExceptionMessage(ex, message.toString());


        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({CustomerAlreadyRegisteredException.class, WorkerAlreadyRegisteredException.class,
            CustomerNotFoundException.class, WorkerNotFoundException.class, ActuationAreaAlreadyRegisteredException.class,
            ActuationAreaNotFoundException.class, JobRequestNotFoundException.class,
            CepNotFoundException.class, JobRequestNotAvailableException.class})
    public ResponseEntity<Object> handleCustomerNotFoundExceptionMethod(Exception ex) {

        ExceptionMessage exceptionMessageObj = buildExceptionMessage(ex, null);
        return new ResponseEntity<>(exceptionMessageObj, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


    private ExceptionMessage buildExceptionMessage(Exception ex, String message) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        if (message == null)
            exceptionMessage.setMessage(ex.getMessage());
        else
            exceptionMessage.setMessage(message);
        logger.info("Error: {}", exceptionMessage.getMessage());
        return exceptionMessage;
    }
}