package ru.nsu.concerts_mate.users_service.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    @JsonProperty(value = "telegram_id")
    private long telegramId;

    @JsonProperty(value = "creation_datetime")
    private Date creationDatetime;
}
