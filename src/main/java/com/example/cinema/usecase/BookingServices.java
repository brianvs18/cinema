package com.example.cinema.usecase;

import com.example.cinema.dto.BookingDTO;
import com.example.cinema.entity.BookingDocument;
import com.example.cinema.factory.BookingFactory;
import com.example.cinema.usecase.common.functions.MovieListFunction;
import com.example.cinema.model.Booking;
import com.example.cinema.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BookingServices implements BookingFactory {

    private final BookingRepository bookingRepository;
    private final MovieListFunction function;

    public Flux<BookingDTO> findAll() {
        return bookingRepository.findAll()
                .map(this::buildBooking)
                .flatMap(this::buildBookingWithMovieList);
    }

    public Mono<BookingDTO> findById(String bookingId){
        return bookingRepository.findById(bookingId)
                .map(this::buildBooking)
                .flatMap(this::buildBookingWithMovieList);
    }

    private Mono<BookingDTO> buildBookingWithMovieList(Booking booking) {
        return function.mapMoviesList(booking.getMovies())
                .map(movieDTOS -> BookingDTO.builder()
                        .id(booking.getId())
                        .userId(booking.getUserId())
                        .showtimeId(booking.getShowtimeId())
                        .movies(movieDTOS)
                        .build());
    }
}
