package com.example.cinema.repository;

import com.example.cinema.entity.MovieDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MovieRepository extends ReactiveMongoRepository<MovieDocument, String> {
}
