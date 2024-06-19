package com.example.toto.controller;

import com.example.toto.exception.ExpiredBattingException;
import com.example.toto.exception.InvalidValueException;
import com.example.toto.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundException(NotFoundException e) {
        return e.getMessage() + " NOT FOUND";
    }

    @ExceptionHandler(InvalidValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidValueException() {
        return "INVALID VALUE";
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String tokenExpiredException() {
        return "TOKEN EXPIRED";
    }

    @ExceptionHandler(ExpiredBattingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String expiredBattingException() {
        return "EXPIRED BETTING";
    }
}
