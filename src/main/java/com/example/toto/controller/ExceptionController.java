package com.example.toto.controller;

import com.example.toto.exception.ExpiredBattingException;
import com.example.toto.exception.InvalidValueException;
import com.example.toto.exception.NotFoundException;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String tokenExpiredException() {
        return "EXPIRED TOKEN";
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String jwtException(JwtException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ExpiredBattingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String expiredBattingException() {
        return "EXPIRED BETTING";
    }

    @ExceptionHandler(FeignException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String feignException(FeignException e) {
        return e.getMessage();
    }

}
