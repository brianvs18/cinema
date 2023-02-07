package com.example.cinema.usecase;

import com.example.cinema.dto.BookingDTO;
import com.example.cinema.entity.BookingDocument;
import com.example.cinema.enums.BookingErrorEnum;
import com.example.cinema.enums.GenericErrorEnum;
import com.example.cinema.exceptions.BookingException;
import com.example.cinema.exceptions.GenericException;
import com.example.cinema.factory.BookingFactory;
import com.example.cinema.model.Booking;
import com.example.cinema.repository.BookingRepository;
import com.example.cinema.usecase.common.functions.MovieListFunction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

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

    public Mono<BookingDTO> findById(String bookingId) {
        return bookingRepository.findById(bookingId)
                .map(this::buildBooking)
                .flatMap(this::buildBookingWithMovieList)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BookingException(BookingErrorEnum.BOOKING_NOT_EXISTS))));
    }

    public Flux<BookingDTO> findAllByUserId(String userId) {
        return bookingRepository.findAllByUserId(userId)
                .map(this::buildBooking)
                .flatMap(this::buildBookingWithMovieList);
    }

    public Mono<BookingDTO> saveBooking(BookingDTO bookingDTO) {
        return Mono.just(bookingDTO)
                .filter(bookingData -> Objects.nonNull(bookingData.getShowtimeId()) && Objects.nonNull(bookingData.getUserId()))
                .map(bookingData -> BookingDocument.builder()
                        .userId(bookingDTO.getUserId())
                        .showtimeId(bookingDTO.getShowtimeId())
                        .movies(bookingDTO.getMovies())
                        .build())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new GenericException(GenericErrorEnum.NON_EMPTY_FIELDS))))
                .flatMap(bookingRepository::save)
                .thenReturn(bookingDTO);
    }

    public Mono<Void> deleteBooking(String bookingId) {
        return bookingRepository.deleteById(bookingId);
    }

    private Mono<BookingDTO> buildBookingWithMovieList(Booking booking) {
        return function.mapMoviesList(booking.getMovies())
                .map(movieDTOS -> BookingDTO.builder()
                        .id(booking.getId())
                        .userId(booking.getUserId())
                        .showtimeId(booking.getShowtimeId())
                        .moviesList(movieDTOS)
                        .build());
    }
}
