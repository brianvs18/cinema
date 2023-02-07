package com.example.cinema.factory;

import com.example.cinema.dto.ShowtimeDTO;
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

    default ShowtimeDocument editBuildShowtime(ShowtimeDTO showtimeDTO){
        return ShowtimeDocument.builder()
                .id(showtimeDTO.getId())
                .date(showtimeDTO.getDate())
                .movies(showtimeDTO.getMovies())
                .build();
    }

    default ShowtimeDocument saveBuildShowtime(ShowtimeDTO showtimeDTO){
        return ShowtimeDocument.builder()
                .date(showtimeDTO.getDate())
                .movies(showtimeDTO.getMovies())
                .build();
    }
}
