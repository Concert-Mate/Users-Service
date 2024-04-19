package ru.nsu.concerts_mate.users_service.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.concerts_mate.users_service.model.dto.ConcertDto;
import ru.nsu.concerts_mate.users_service.model.dto.UserDto;

import java.util.List;

@Data
@AllArgsConstructor
public class RabbitMqEvent {
    private UserDto user;

    private List<ConcertDto> concerts;
}
