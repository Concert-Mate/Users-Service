package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.Data;

@Data
public class UserTracksListDto {
    private long userId;

    private String tracksListUrl;
}
