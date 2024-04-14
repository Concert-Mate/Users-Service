package ru.nsu.concertsmate.backend.model.dto;

import lombok.Data;

@Data
public class UserTracksListDto {
    private long userId;
    private String tracksListUrl;
}
