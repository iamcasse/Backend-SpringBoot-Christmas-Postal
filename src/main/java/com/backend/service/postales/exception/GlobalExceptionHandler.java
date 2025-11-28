package com.backend.service.postales.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        Map<String, String> message = new HashMap<>();
        message.put("message", resourceNotFoundException.getMessage());

        return message;

    }

    @ExceptionHandler({DuplicatePostalException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicatePostalException(DuplicatePostalException duplicatePostalException) {
        Map<String, String> message = new HashMap<>();
        message.put("message", duplicatePostalException.getMessage());

        return message;

    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }



}
