package com.example.cinema.exceptions;

import com.example.cinema.enums.GenericErrorEnum;

public class GenericException extends RuntimeException{
    public GenericException(GenericErrorEnum error){
        super(error.name());
    }
}
