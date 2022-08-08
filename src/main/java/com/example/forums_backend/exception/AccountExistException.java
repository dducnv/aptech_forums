package com.example.forums_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AccountExistException extends Exception{

    public AccountExistException(String errorMessage) {
        super(errorMessage);
    }
}
