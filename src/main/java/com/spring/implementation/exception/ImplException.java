package com.spring.implementation.exception;

import com.spring.implementation.dto.enums.ResponseCode;
import lombok.Getter;

@Getter
public class ImplException extends Exception{

    private ResponseCode errorCode;
    private String[] fields;
    private Exception exception;

    public ImplException() {
        super("Failed to do operation");
        this.errorCode = ResponseCode.INTERNAL_ERROR;
        this.exception = new RuntimeException();
    }

    public ImplException(ResponseCode code, String message, String... fields) {
        super(message);
        this.errorCode = code;
        this.fields = fields;
    }

    public ImplException(Exception exception) {
        super(exception.getLocalizedMessage());
        this.errorCode = ResponseCode.INTERNAL_ERROR;
        this.exception = exception;
    }
}
