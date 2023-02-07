package com.example.cinema.controller;

import com.example.cinema.dto.ShowtimeDTO;
import com.example.cinema.usecase.ShowtimeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeServices showtimeServices;

    @GetMapping("/showtime")
    public Flux<ShowtimeDTO> findAll(){
        return showtimeServices.findAll();
    }

    @GetMapping("/showtime/{id}")
    public Mono<ShowtimeDTO> findById(@PathVariable(value = "id") String showtimeId){
        return showtimeServices.findById(showtimeId);
    }
}
