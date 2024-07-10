package com.amalitech.AccessKey.exception;

public class NotAuthenticatedException extends RuntimeException{
    public NotAuthenticatedException(String message){
        super(message);
    }
}
