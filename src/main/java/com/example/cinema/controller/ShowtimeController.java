package com.example.cinema.controller;

import com.example.cinema.dto.ShowtimeDTO;
import com.example.cinema.usecase.ShowtimeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeServices showtimeServices;

    @GetMapping("/api/showtime")
    public Flux<ShowtimeDTO> findAll() {
        return showtimeServices.findAll();
    }

    @GetMapping("/api/showtime/{id}")
    public Mono<ShowtimeDTO> findById(@PathVariable(value = "id") String showtimeId) {
        return showtimeServices.findById(showtimeId);
    }

    @PostMapping("/api/showtime")
    public Mono<ShowtimeDTO> saveShowtime(@RequestBody ShowtimeDTO showtimeDTO) {
        return showtimeServices.saveShowtime(showtimeDTO);
    }

    @GetMapping(path = "/api/showtime", params = {"movieId"})
    public Flux<ShowtimeDTO> findAllCriteria(@RequestParam("movieId") String movieId) {
        return showtimeServices.findByMovieIdCriteria(movieId);
    }
}
