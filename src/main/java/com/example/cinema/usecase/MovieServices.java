package com.example.cinema.usecase;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.enums.MovieErrorEnum;
import com.example.cinema.exceptions.MovieException;
import com.example.cinema.factory.MovieFactory;
import com.example.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieServices implements MovieFactory {

    private final MovieRepository movieRepository;

    public Flux<MovieDTO> findAll(){
        return movieRepository.findAll()
                .map(this::buildMovieDTO);
    }

    public Mono<MovieDTO> findById(String movieId){
        return movieRepository.findById(movieId)
                .map(this::buildMovieDTO);
    }

    public Mono<MovieDTO> saveMovie(MovieDTO movieDTO){
        return Mono.just(movieDTO)
                .filter(movieData -> Objects.nonNull(movieDTO.getId()))
                .flatMap(movieDTO1 -> findById(movieDTO.getId())
                        .map(this::editBuildMovie)
                        .switchIfEmpty(Mono.defer(()-> Mono.error(new MovieException(MovieErrorEnum.MOVIE_IS_NOT_EXISTS))))
                )
                .switchIfEmpty(Mono.defer(()-> Mono.just(movieDTO)
                        .filter(user -> Objects.nonNull(movieDTO.getTitle()))
                        .filter(user -> Objects.nonNull(movieDTO.getDirector()))
                        .map(this::saveBuildMovie)
                ))
                .flatMap(movieRepository::save)
                .switchIfEmpty(Mono.defer(()-> Mono.error(new MovieException(GenericErrorEnum.NON_EMPTY_FIELDS))))
                .thenReturn(movieDTO);
    }

    public Mono<Void> deleteMovie(String movieId){
        return movieRepository.deleteById(movieId);
    }

}
