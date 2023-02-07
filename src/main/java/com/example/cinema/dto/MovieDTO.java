package com.example.cinema.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MovieDTO {
    private String id;
    private String title;
    private String director;
    private Integer rating;
}
