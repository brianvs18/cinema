package com.example.cinema.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShowtimeDTO {
    private String id;
    private Long date;
    private List<MovieDTO> moviesList;
    private List<String> movies;
}
