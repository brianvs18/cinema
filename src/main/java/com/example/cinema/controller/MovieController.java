package com.example.cinema.controller;

import com.example.cinema.dto.MovieDTO;
import com.example.cinema.usecase.MovieServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final MovieServices movieServices;

    @GetMapping("/api/movies")
    public Flux<MovieDTO> findAll(){
        return movieServices.findAll();
    }

    @GetMapping("/api/movies/{id}")
    public Mono<MovieDTO> findById(@PathVariable(value = "id") String movieId){
        return movieServices.findById(movieId);
    }

    @PostMapping("/api/movies")
    public Mono<MovieDTO> saveMovie(@RequestBody MovieDTO movie){
        return movieServices.saveMovie(movie);
    }

    @DeleteMapping("/api/movies/{id}")
    public Mono<Void> deleteMovie(@PathVariable(value = "id") String movieId){
        return movieServices.deleteMovie(movieId);
    }
}
