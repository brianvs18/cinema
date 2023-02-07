package com.example.cinema.usecase;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.dto.ShowtimeDTO;
import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.enums.ShowtimeErrorEnum;
import com.example.cinema.exceptions.MovieException;
import com.example.cinema.exceptions.ShowtimeException;
import com.example.cinema.factory.ShowtimeFactory;
import com.example.cinema.model.Showtime;
import com.example.cinema.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

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

    private Mono<List<MovieDTO>> mapMoviesList(List<String> movies) {
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
        return mapMoviesList(showtime.getMovies())
                .map(movieDTOS -> ShowtimeDTO.builder()
                        .id(showtime.getId())
                        .date(showtime.getDate())
                        .moviesList(movieDTOS)
                        .build());
    }

    public Mono<ShowtimeDTO> saveShowtime(ShowtimeDTO showtimeDTO){
        return Mono.just(showtimeDTO)
                .filter(showtimeData -> Objects.nonNull(showtimeData.getDate()))
                .filter(showtimeData -> Objects.nonNull(showtimeDTO.getId()))
                .flatMap(showtimeData -> findById(showtimeDTO.getId())
                        .map(this::editBuildShowtime)
                        .switchIfEmpty(Mono.defer(()-> Mono.error(new ShowtimeException(ShowtimeErrorEnum.SHOWTIME_IS_NOT_EXISTS))))
                )
                .switchIfEmpty(Mono.defer(()-> Mono.just(showtimeDTO)
                        .map(this::saveBuildShowtime)
                ))
                .flatMap(showtimeRepository::save)
                .switchIfEmpty(Mono.defer(()-> Mono.error(new MovieException(GenericErrorEnum.NON_EMPTY_FIELDS))))
                .thenReturn(showtimeDTO);
    }
}
