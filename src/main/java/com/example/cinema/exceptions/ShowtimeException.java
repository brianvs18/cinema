package com.example.cinema.exceptions;

import com.example.cinema.enums.ShowtimeErrorEnum;

public class ShowtimeException extends RuntimeException{

    public ShowtimeException(ShowtimeErrorEnum error){
        super(error.name());
    }
}
