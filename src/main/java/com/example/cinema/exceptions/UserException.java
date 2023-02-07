package com.example.cinema.exceptions;

import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.enums.UserErrorEnum;

public class UserException extends RuntimeException{

    public UserException(UserErrorEnum error){
        super(error.name());
    }

    public UserException(GenericErrorEnum error){
        super(error.name());
    }
}
