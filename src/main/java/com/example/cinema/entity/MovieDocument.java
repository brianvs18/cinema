package com.example.cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document
public class MovieDocument {
    @Id
    private String id;
    private String title;
    private String director;
    private Integer rating;
}
