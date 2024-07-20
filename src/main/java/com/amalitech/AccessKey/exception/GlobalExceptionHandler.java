package com.amalitech.AccessKey.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();
        return exceptionResponse(HttpStatus.BAD_REQUEST, String.join(", ", errors));
    }

    @ExceptionHandler({Exception.class, VerificationFailedException.class, DuplicateEmailException.class})
    public ResponseEntity<Object> handleGenericException(Exception e) {
        return exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handle404Exceptions(NotFoundException e) {
        return exceptionResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({ActiveAccessKeyException.class})
    public ResponseEntity<Object> handleBadRequests(ActiveAccessKeyException e) {
        return exceptionResponse(HttpStatus.BAD_REQUEST, e.getMessage());
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


