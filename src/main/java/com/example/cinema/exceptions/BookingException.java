package com.example.cinema.exceptions;

import com.example.cinema.enums.BookingErrorEnum;

public class BookingException extends RuntimeException{
    public BookingException(BookingErrorEnum error){
        super(error.name());
    }
}
