package com.spring.implementation.controller;

import com.spring.implementation.dto.Response;
import org.jboss.resteasy.spi.HttpResponseCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AppController {

    public ResponseEntity<Response> data(HttpStatus code, String message, Object data) {
        return new ResponseEntity<>(Response.builder()
                .code(code)
                .data(data)
                .message(message)
                .build(), HttpStatus.OK);
    }
}
