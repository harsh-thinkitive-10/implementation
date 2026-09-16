package com.spring.implementation.exception;

import com.spring.implementation.dto.ErrorResponse;
import com.spring.implementation.dto.Response;
import com.spring.implementation.dto.enums.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler  {

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @ExceptionHandler(ImplException.class)
    protected ResponseEntity<Object> handleCustomException(ImplException exception, WebRequest request) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        switch (exception.getErrorCode()) {
            case UNSUPPORTED_MEDIA_TYPE -> httpStatus = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            case NOT_FOUND -> httpStatus = HttpStatus.NOT_FOUND;
            case CONFLICT -> httpStatus = HttpStatus.CONFLICT;
            case ACCESS_DENIED, UNAUTHORIZED -> httpStatus = HttpStatus.FORBIDDEN;
            case TOO_MANY_REQUESTS -> httpStatus = HttpStatus.TOO_MANY_REQUESTS;
            case INTERNAL_ERROR, DB_ERROR, IAM_ERROR, AWS_ERROR -> httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        HttpHeaders responseHeaders = httpHeaders;
        if (exception.getErrorCode() == ResponseCode.TOO_MANY_REQUESTS
                && exception.getFields() != null && exception.getFields().length > 0) {
            responseHeaders = new HttpHeaders();
            responseHeaders.set("Retry-After", exception.getFields()[0]);
        }

        return handleExceptionInternal(exception, buildResponse(exception.getErrorCode(), exception.getMessage(), request), responseHeaders, httpStatus, request);
    }

    @ExceptionHandler(PatientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePatientNotFound(PatientNotFoundException exception, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Patient Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }


//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request){
//        Map<String,String> errors = new HashMap<>();
//        exception.getBindingResult()
//                .getFieldErrors()
//                .forEach(error ->
//                        errors.put(
//                                error.getField(),
//                                error.getDefaultMessage()
//                        )
//                );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }

    @ExceptionHandler(UserNameAlreadyExitsException.class)
    public ResponseEntity<ErrorResponse> handleUserNameAlreadyTakenException(UserNameAlreadyExitsException exception, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                "username is already exits.",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException exception,
            HttpServletRequest request
    ) {

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(AppointmentNotFound.class)
    public ResponseEntity<ErrorResponse> handleAppointmentNotFound(
            AppointmentNotFound appoinmentNotFound, HttpServletRequest request
    ){
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Appointment Not Found",
                appoinmentNotFound.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    private Response buildResponse(ResponseCode code, String message, WebRequest request) {
        return Response.builder()
                .code(code)
                .message(message)
                .build();
    }
}
