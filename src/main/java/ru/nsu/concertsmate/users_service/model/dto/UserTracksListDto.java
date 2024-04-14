package ru.nsu.concertsmate.users_service.model.dto;

import lombok.Data;

@Data
public class UserTracksListDto {
    private long userId;

    private String tracksListUrl;
}
