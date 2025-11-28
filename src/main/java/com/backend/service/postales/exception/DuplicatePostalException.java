package com.backend.service.postales.exception;

public class DuplicatePostalException extends RuntimeException {
    public DuplicatePostalException(String message) {
        super(message);
    }
}
