package com.example.cinema.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Document
public class BookingDocument {
    @Id
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> movies;
}
