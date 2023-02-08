package com.example.cinema.usecase.common.functions;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieListFunction {
    private final MovieRepository movieRepository;

    public Mono<List<MovieDTO>> mapMoviesList(List<String> movies) {
        return movieRepository.findAllById(movies)
                .map(movieDTO -> MovieDTO.builder()
                        .id(movieDTO.getId())
                        .title(movieDTO.getTitle())
                        .director(movieDTO.getDirector())
                        .rating(movieDTO.getRating())
                        .build())
                .collectList();
    }
}
