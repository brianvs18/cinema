package com.example.cinema.exceptions;

import com.example.cinema.enums.MovieErrorEnum;

public class MovieException extends RuntimeException{
    public MovieException(MovieErrorEnum error){
        super(error.name());
    }
}
