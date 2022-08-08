package com.example.forums_backend.exception;

public class AccountExistException extends Exception{
    public AccountExistException(String errorMessage) {
        super(errorMessage);
    }
}
