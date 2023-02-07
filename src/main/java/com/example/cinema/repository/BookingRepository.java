package com.example.cinema.repository;

import com.example.cinema.entity.BookingDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BookingRepository extends ReactiveMongoRepository<BookingDocument, String> {
}
