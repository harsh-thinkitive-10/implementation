package com.spring.implementation.dto;

import com.spring.implementation.dto.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.jboss.resteasy.spi.HttpResponseCodes;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Response {

    private ResponseCode code;

    private Object data;

    private String message;

}
