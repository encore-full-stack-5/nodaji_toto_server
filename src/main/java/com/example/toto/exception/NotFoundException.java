package com.example.toto.exception;

public class NotFoundException extends IllegalArgumentException{
    public NotFoundException(String target){
        super(target);
    }
}
