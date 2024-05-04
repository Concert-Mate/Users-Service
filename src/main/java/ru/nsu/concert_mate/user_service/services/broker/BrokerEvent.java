package ru.nsu.concert_mate.user_service.services.broker;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.nsu.concert_mate.user_service.model.dto.ConcertDto;
import ru.nsu.concert_mate.user_service.model.dto.UserDto;

import java.util.List;

@Data
@AllArgsConstructor
public class BrokerEvent {
    private UserDto user;

    private List<ConcertDto> concerts;
}
