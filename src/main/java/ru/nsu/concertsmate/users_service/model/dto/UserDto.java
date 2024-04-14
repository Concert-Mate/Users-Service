package ru.nsu.concertsmate.users_service.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {
    private long telegramId;

    private Date creationDatetime;
}
