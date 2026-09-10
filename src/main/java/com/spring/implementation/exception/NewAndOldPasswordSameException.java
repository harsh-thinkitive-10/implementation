package com.spring.implementation.exception;

public class NewAndOldPasswordSameException extends RuntimeException {
    public NewAndOldPasswordSameException(String message) {
        super(message);
    }
}
