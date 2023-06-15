package com.kiraz.messengerapp.exception;

public class EmailNotFoundException extends Exception{
    public EmailNotFoundException(String errorMessage){
        super(errorMessage);
    }
}
