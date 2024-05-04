package ru.nsu.concert_mate.user_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackListHeaderDto {
    private String url;

    private String title;
}
