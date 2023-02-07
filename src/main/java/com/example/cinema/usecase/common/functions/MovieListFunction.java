package com.example.cinema.usecase.common.functions;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.usecase.MovieServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovieListFunction {
    private final MovieServices movieServices;

    public Mono<List<MovieDTO>> mapMoviesList(List<String> movies) {
        return movieServices.findByIdIn(movies)
                .map(movieDTO -> movieDTO.toBuilder()
                        .id(movieDTO.getId())
                        .title(movieDTO.getTitle())
                        .director(movieDTO.getDirector())
                        .rating(movieDTO.getRating())
                        .build())
                .collectList();
    }
}
