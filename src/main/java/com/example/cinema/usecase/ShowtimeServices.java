package com.example.cinema.usecase;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.dto.ShowtimeDTO;
import com.example.cinema.factory.ShowtimeFactory;
import com.example.cinema.model.Showtime;
import com.example.cinema.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShowtimeServices implements ShowtimeFactory {

    private final ShowtimeRepository showtimeRepository;
    private final MovieServices movieServices;

    public Flux<ShowtimeDTO> findAll() {
        return showtimeRepository.findAll()
                .map(this::buildShowtime)
                .flatMap(this::buildShowtimeWithMovieList);
    }

    public Mono<ShowtimeDTO> findById(String showtimeId) {
        return showtimeRepository.findById(showtimeId)
                .map(this::buildShowtime)
                .flatMap(this::buildShowtimeWithMovieList);
    }

    private Mono<List<MovieDTO>> convertToMoviesList(List<String> movies) {
        return movieServices.findByIdIn(movies)
                .map(movieDTO -> movieDTO.toBuilder()
                        .id(movieDTO.getId())
                        .title(movieDTO.getTitle())
                        .director(movieDTO.getDirector())
                        .rating(movieDTO.getRating())
                        .build())
                .collectList();
    }

    private Mono<ShowtimeDTO> buildShowtimeWithMovieList(Showtime showtime) {
        return convertToMoviesList(showtime.getMovies())
                .map(movieDTOS -> ShowtimeDTO.builder()
                        .id(showtime.getId())
                        .date(showtime.getDate())
                        .moviesList(movieDTOS)
                        .build());
    }
}
