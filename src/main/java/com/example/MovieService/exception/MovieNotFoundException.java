package com.example.MovieService.exception;

public class MovieNotFoundException extends Exception{

    public MovieNotFoundException(String text){
        super(text);
    }
}
