package com.spring.implementation.exception;

public class UserNameAlreadyExitsException extends RuntimeException{
    public UserNameAlreadyExitsException(String msg){super(msg);}
}
