package com.example.MovieService.exception;

public class BadDataException extends Exception{
    public BadDataException(String text){
        super(text);
    }
}
