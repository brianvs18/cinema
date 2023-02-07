package com.example.cinema.factory;

import com.example.cinema.entity.ShowtimeDocument;
import com.example.cinema.model.Showtime;

public interface ShowtimeFactory {

    default Showtime buildShowtime(ShowtimeDocument showtimeDocument){
        return Showtime.builder()
                .id(showtimeDocument.getId())
                .date(showtimeDocument.getDate())
                .movies(showtimeDocument.getMovies())
                .build();
    }
}
