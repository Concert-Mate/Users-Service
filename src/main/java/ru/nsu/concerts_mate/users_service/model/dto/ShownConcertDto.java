package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.Data;

@Data
public class ShownConcertDto {
    private long userId;

    private String concertUrl;
}
