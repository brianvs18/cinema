package com.example.cinema.model;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Showtime {
    private String id;
    private Long date;
    private List<String> movies;
}
