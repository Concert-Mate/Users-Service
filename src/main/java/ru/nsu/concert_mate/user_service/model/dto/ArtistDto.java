package ru.nsu.concert_mate.user_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArtistDto {
    private String name;

    @JsonProperty(value = "yandex_music_id")
    private int yandexMusicId;
}
