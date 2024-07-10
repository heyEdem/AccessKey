package com.amalitech.AccessKey;

public class NotAuthenticatedException extends RuntimeException{
    public NotAuthenticatedException(String message){
        super(message);
    }
}
