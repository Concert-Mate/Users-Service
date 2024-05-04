package ru.nsu.concert_mate.user_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrackListDto {
    private String url;

    private String title;

    @JsonProperty(value = "image_url")
    private String imageUrl;

    private List<ArtistDto> artists;
}
