package com.example.cinema.controller;

import com.example.cinema.dto.BookingDTO;
import com.example.cinema.usecase.BookingServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingServices bookingServices;
    @GetMapping("/api/bookings")
    public Flux<BookingDTO> findAll(){
        return bookingServices.findAll();
    }

    @GetMapping("/api/bookings/{id}")
    public Mono<BookingDTO> findById(@PathVariable(value = "id") String bookingId){
        return bookingServices.findById(bookingId);
    }

    /*@PostMapping("/api/bookings")
    public Mono<BookingDTO> saveBooking(@RequestBody BookingDTO booking){
        return bookingServices.saveBooking(booking);
    }

    @DeleteMapping("/api/bookings/{id}")
    public Mono<Void> deleteBooking(@PathVariable(value = "id") String bookingId){
        return bookingServices.deleteBooking(bookingId);
    }*/
}
