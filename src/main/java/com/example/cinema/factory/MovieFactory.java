package com.example.cinema.factory;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.entity.MovieDocument;

public interface MovieFactory {

    default MovieDTO buildMovieDTO(MovieDocument movieDocument){
        return MovieDTO.builder()
                .id(movieDocument.getId())
                .title(movieDocument.getTitle())
                .director(movieDocument.getDirector())
                .rating(movieDocument.getRating())
                .build();
    }

    default MovieDocument editBuildMovie(MovieDTO movieDTO){
        return MovieDocument.builder()
                .id(movieDTO.getId())
                .title(movieDTO.getTitle())
                .director(movieDTO.getDirector())
                .rating(movieDTO.getRating())
                .build();
    }

    default MovieDocument saveBuildMovie(MovieDTO movieDTO){
        return MovieDocument.builder()
                .title(movieDTO.getTitle())
                .director(movieDTO.getDirector())
                .rating(movieDTO.getRating())
                .build();
    }
}
