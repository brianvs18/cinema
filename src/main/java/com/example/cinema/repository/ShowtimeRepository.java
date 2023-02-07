package com.example.cinema.repository;

import com.example.cinema.entity.ShowtimeDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ShowtimeRepository extends ReactiveMongoRepository<ShowtimeDocument, String> {
}
