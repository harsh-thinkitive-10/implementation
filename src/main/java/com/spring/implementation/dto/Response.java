package com.spring.implementation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jboss.resteasy.spi.HttpResponseCodes;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response {

    private HttpStatus code;

    private Object data;

    private String message;

}
