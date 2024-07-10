package com.amalitech.AccessKey.exception;

public class VerificationFailedException extends RuntimeException{
    public VerificationFailedException(String message) {
        super(message);
    }
}
