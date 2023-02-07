package com.example.cinema.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Booking {
    private String id;
    private String userId;
    private String showtimeId;
    private List<String> movies;
}
