package com.example.cinema.repository;

import com.example.cinema.entity.BookingDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookingRepository extends ReactiveMongoRepository<BookingDocument, String> {
    Flux<BookingDocument> findAllByUserId(String userId);
}
