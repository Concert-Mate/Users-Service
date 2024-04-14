package ru.nsu.concertsmate.backend.api;

import lombok.Data;
import ru.nsu.concertsmate.backend.model.dto.ConcertDto;

import java.util.List;

@Data
public class UserConcertsResponse {
    private ResponseStatus status;

    private List<ConcertDto> concerts;
}
