package ru.nsu.concertsmate.backend.model.dto;

import lombok.Data;


import java.util.Date;

@Data
public class UserDto {
    private long telegramId;

    private Date creationDatetime;
}
