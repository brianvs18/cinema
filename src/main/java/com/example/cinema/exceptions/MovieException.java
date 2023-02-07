package com.example.cinema.exceptions;

import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.enums.MovieErrorEnum;
import com.example.cinema.enums.UserErrorEnum;

public class MovieException extends RuntimeException{
    public MovieException(MovieErrorEnum error){
        super(error.name());
    }

    public MovieException(GenericErrorEnum error){
        super(error.name());
    }
}
