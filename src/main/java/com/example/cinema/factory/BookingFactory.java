package com.example.cinema.factory;

import com.example.cinema.entity.BookingDocument;
import com.example.cinema.model.Booking;

public interface BookingFactory {
    default Booking buildBooking(BookingDocument bookingDocument) {
        return Booking.builder()
                .id(bookingDocument.getId())
                .userId(bookingDocument.getUserId())
                .showtimeId(bookingDocument.getShowtimeId())
                .movies(bookingDocument.getMovies())
                .build();
    }
}
