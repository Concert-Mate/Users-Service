package ru.nsu.concertsmate.backend.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class TracksListDto {
    private String url;

    private String title;

    private String imageUrl;

    private List<ArtistDto> artists;
}
