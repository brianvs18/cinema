package com.example.cinema.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ShowtimeDTO {
    private String id;
    private Long date;
    private List<MovieDTO> moviesList;
}
