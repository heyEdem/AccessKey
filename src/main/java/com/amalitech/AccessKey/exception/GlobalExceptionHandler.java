package com.amalitech.AccessKey.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({Exception.class, VerificationFailedException.class, DuplicateEmailException.class})
    public ResponseEntity<Object> handleGenericException(Exception e) {
        return exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handle404Exceptions(NotFoundException e) {
        return exceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({NotAuthenticatedException.class})
    public ResponseEntity<Object> handle403Exceptions(NotFoundException e) {
        return exceptionResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    private ResponseEntity<Object> exceptionResponse(HttpStatus status, String details) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setDetail(details);
        return ResponseEntity.status(status).body(problemDetail);
    }
}


