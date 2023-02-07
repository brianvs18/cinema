package com.example.cinema.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookingDTO {
    private String id;
    private String userId;
    private String showtimeId;
    private List<MovieDTO> moviesList;
    private List<String> movies;
}
