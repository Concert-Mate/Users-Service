package ru.nsu.concerts_mate.users_service.model.dto;

import lombok.Data;

@Data
public class UserCityDto {
    private long userId;

    private String cityName;
}
