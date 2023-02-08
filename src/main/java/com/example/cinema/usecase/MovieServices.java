package com.example.cinema.usecase;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.enums.MovieErrorEnum;
import com.example.cinema.exceptions.GenericException;
import com.example.cinema.exceptions.MovieException;
import com.example.cinema.factory.MovieFactory;
import com.example.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieServices implements MovieFactory {

    private final MovieRepository movieRepository;
    private final ShowtimeServices showtimeServices;
    private static final Integer MAXIMUM_RANGE = 5;

    public Flux<MovieDTO> findAll() {
        return movieRepository.findAll()
                .map(this::buildMovieDTO);
    }

    public Mono<MovieDTO> findById(String movieId) {
        return movieRepository.findById(movieId)
                .map(this::buildMovieDTO)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new MovieException(MovieErrorEnum.MOVIE_IS_NOT_EXISTS))));
    }

    public Mono<MovieDTO> saveMovie(MovieDTO movieDTO) {
        return Mono.just(movieDTO)
                .flatMap(MovieServices::validateRatingRangeMovie)
                .flatMap(MovieServices::validateData)
                .filter(movieData -> Objects.nonNull(movieDTO.getId()))
                .flatMap(movieData -> findById(movieDTO.getId())
                        .map(movieDB -> editBuildMovie(movieDTO, movieDB))
                        .switchIfEmpty(Mono.defer(() -> Mono.error(new MovieException(MovieErrorEnum.MOVIE_IS_NOT_EXISTS))))
                )
                .switchIfEmpty(Mono.defer(() -> Mono.just(movieDTO)
                        .map(this::saveBuildMovie)
                ))
                .flatMap(movieRepository::save)
                .thenReturn(movieDTO);
    }

    private static Mono<MovieDTO> validateData(MovieDTO movieDTO) {
        return Mono.just(movieDTO)
                .filter(user -> StringUtils.isNotBlank(movieDTO.getTitle()) && StringUtils.isNotBlank(movieDTO.getDirector()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GenericException(GenericErrorEnum.NO_EMPTY_FIELDS))));
    }

    private static Mono<MovieDTO> validateRatingRangeMovie(MovieDTO movieDTO) {
        return Mono.just(movieDTO)
                .filter(movieData -> movieData.getRating() >= NumberUtils.INTEGER_ONE && movieData.getRating() <= MAXIMUM_RANGE)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new MovieException(MovieErrorEnum.NO_RANGE_RATING_MOVIE))));
    }

    public Mono<Void> deleteMovie(String movieId) {
        return showtimeServices.findByMovieId(movieId)
                .switchIfEmpty(movieRepository.deleteById(movieId).then(Mono.empty()))
                .then();
    }
}
