package com.spring.implementation.exception;

public class AppointmentNotFound extends RuntimeException {
    public AppointmentNotFound(String message) {
        super(message);
    }
}
